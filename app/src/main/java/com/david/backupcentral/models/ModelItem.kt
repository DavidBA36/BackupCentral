package com.david.backupcentral.models

import android.os.Parcel
import android.os.Parcelable

class ModelItem( val itemID:Int, val itemIMG:Int, val itemName: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(itemID)
        parcel.writeInt(itemIMG)
        parcel.writeString(itemName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelItem> {
        override fun createFromParcel(parcel: Parcel): ModelItem {
            return ModelItem(parcel)
        }

        override fun newArray(size: Int): Array<ModelItem?> {
            return arrayOfNulls(size)
        }
    }

}
