package com.spartons.passengerapp.collection

import com.google.android.gms.maps.model.Marker
import java.util.*

 object  MarkerCollection {
         @JvmStatic
         private var markers: MutableList<Marker> = LinkedList()
         @JvmStatic
         fun insertMarker(marker: Marker) = apply {
             markers.add(marker)
         }
         @JvmStatic
         fun getMarker(driverId: String): Marker? {
             for (marker in markers)
                 if (marker.tag == driverId) return marker
             return null
         }
         @JvmStatic
         fun clearMarkers() = apply {
             markers.clear()
         }
         @JvmStatic
         fun removeMarker(driverId: String) {
             val marker = getMarker(driverId)
             marker?.remove()
             if (marker != null) markers.remove(marker)
         }
         @JvmStatic
         fun allMarkers() = markers

}
