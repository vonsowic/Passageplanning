package com.bearcave.passageplanning.base.database

import android.os.Parcelable
import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey

/**
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 19.05.17.
 */

interface DatabaseElement : DatabaseElementWithCustomKey<Int>, Parcelable
