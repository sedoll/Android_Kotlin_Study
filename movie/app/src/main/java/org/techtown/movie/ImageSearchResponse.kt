package org.techtown.movie

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse (
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    var documents: MutableList<KakaoImage>?
        )