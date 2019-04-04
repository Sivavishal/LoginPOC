package com.poc.loginpoc.mvp;

/**
 * Each presenter must implement this interface
 *
 * @param <V> View for the presenter
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     * Called when view attached to presenter
     *
     * @param view view
     */
    void attach(V view);

    /**
     * Called when view is detached from presenter
     */
    void detach();

    /**
     * @return true if view is attached to presenter
     */
    boolean isAttached();
}
