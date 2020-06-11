@file:Suppress("NOTHING_TO_INLINE")

package com.kadencelibrary.extension.context

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

typealias FragmentFactory<F> = () -> F

inline fun <F : Fragment> FragmentManager.popOrReplaceRoot(
    replacement: Replacement<F>,
    tag: String
): F {
    var fragment = pop<F>(tag)
    if (fragment == null) {
        fragment = replacement.fragmentFactory()
        transaction { newBackStack(replacement.containerId, fragment, tag, false) }
    }
    return fragment
}


inline fun <F : Fragment> FragmentManager.addToStack(
    replacement: Replacement<F>,
    tag: String
): F {
    val fragment = replacement.fragmentFactory()
    transaction { addToBackStack(replacement.containerId, fragment, tag, false) }
    return fragment
}


inline fun <F : Fragment> FragmentManager.addToStack(
    fragment: F,
    containerId: Int,
    tag: String
): F {
    transaction { addToBackStack(containerId, fragment, tag, false) }
    return fragment
}


data class Replacement<F : Fragment>(
    @param:IdRes @field:IdRes val containerId: Int,
    val fragmentFactory: FragmentFactory<F>
)

inline infix fun <F : Fragment> FragmentFactory<F>.into(@IdRes containerId: Int): Replacement<F> =
    Replacement(containerId, this)

@Suppress("UNCHECKED_CAST")
inline fun <F : Fragment> FragmentManager.pop(tag: String, inclusive: Boolean = false): F? {
    val fragment = findFragmentByTag(tag) as F?
    if (fragment != null) {
        when (isInBackStack(tag)) {
            true -> {
                val flags = if (inclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0
                popBackStackImmediate(tag, flags)
            } // 0 means that fragments will be popped to fragment specified by tag
            false -> popBackStackImmediate(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            ) // Not a back stack entry (root)
        }
    }
    return fragment
}

inline fun FragmentManager.isInBackStack(tag: String): Boolean = (0 until backStackEntryCount)
    .map(::getBackStackEntryAt)
    .map(FragmentManager.BackStackEntry::getName)
    .any { backStackName -> backStackName == tag }

inline fun FragmentManager.removeAll() = commitNow {
    fragments.reversed().forEach { fragment -> remove(fragment) }
}

inline fun FragmentManager.transaction(
    allowStateLoss: Boolean = false,
    body: FragmentTransactionBuilder.() -> Unit
) = FragmentTransactionBuilder(
    this,
    allowStateLoss
).body()

class FragmentTransactionBuilder constructor(
    private val fragmentManager: FragmentManager,
    private val allowStateLoss: Boolean = false
) {

    /** Clears previous backstack and then adds new [fragment] in [containerId] */
    fun newBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        tag: String,
        animate: Boolean = false
    ) {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragmentManager.commitNow(allowStateLoss = allowStateLoss) {
            if (animate) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(containerId, fragment, tag)
        }
    }

    /** Replaces an previous fragment in [containerId] by new [fragment] */
    fun addToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        tag: String,
        animate: Boolean = true
    ) {
        fragmentManager.commit(allowStateLoss = allowStateLoss) {
            addToBackStack(tag)
            if (animate) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(containerId, fragment, tag)
        }
    }

}

inline fun FragmentManager.commit(
    allowStateLoss: Boolean = false,
    body: FragmentTransaction.() -> Unit
) {
    val transaction = beginTransaction()
    transaction.body()
    if (allowStateLoss) {
        transaction.commitAllowingStateLoss()
    } else {
        transaction.commit()
    }
    executePendingTransactions()
}

inline fun FragmentManager.commitNow(
    allowStateLoss: Boolean = false,
    body: FragmentTransaction.() -> Unit
) {
    val transaction = beginTransaction()
    transaction.body()
    if (allowStateLoss) {
        transaction.commitNowAllowingStateLoss()
    } else {
        transaction.commitNow()
    }
}
