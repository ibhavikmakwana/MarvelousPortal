package com.marvelousportal

/**
 * Created by Krunal-PC on 12/26/2017..
 *
 * Enables Injections for the dataSource
 *
 * @author {@link <a href="https://github.com/krunal3kapadiya" />}
 */
object Injection {
    /**
     * Returns the ViewModel to the activity
     */
    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory()
    }
}