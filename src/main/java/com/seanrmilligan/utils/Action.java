package com.seanrmilligan.utils;

/**
 * Created by sean on 6/6/17.
 */

/*
 * ACTIONS
 * Potential selections for when the user exits the view.
 *
 * UNSET is the default state that should be set whenever the
 * view is presented. The state will change only upon action
 * taken by the user so we can know for sure that an action was
 * taken.
 *
 * SUBMIT would be set upon a text field experiencing the
 * enter key being pressed or a submit button being clicked.
 *
 * CANCEL, if not in a workflow would close the window, possibly
 * also presenting a confirmation dialog if information that was
 * entered would be lost. In a multidialog process it also have
 * the effect of breaking out of the sequence of dialogs.
 *
 */
public enum Action {
    UNSET,
    OK,
    SUBMIT,
    YES,
    NO,
    NEXT,
    PREV,
    CANCEL,
    CLOSE,
    NEW,
    LOAD
}
