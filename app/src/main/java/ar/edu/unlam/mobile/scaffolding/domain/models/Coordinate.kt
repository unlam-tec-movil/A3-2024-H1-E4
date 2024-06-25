package ar.edu.unlam.mobile.scaffolding.domain.models

import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import com.mapbox.geojson.Point

data class Coordinate(
    val latitude: Double,
    val longitude: Double,
    val speed: Float,
    val distance: Float,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readFloat(),
        parcel.readFloat(),
    )

    fun toPoint(): Point = Point.fromLngLat(longitude, latitude)

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int,
    ) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeFloat(speed)
        parcel.writeFloat(distance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coordinate> {
        override fun createFromParcel(parcel: Parcel): Coordinate {
            return Coordinate(parcel)
        }

        override fun newArray(size: Int): Array<Coordinate?> {
            return arrayOfNulls(size)
        }
    }
}

fun Location.toCoordinate(distance: Float): Coordinate = Coordinate(latitude, longitude, speed, distance)
