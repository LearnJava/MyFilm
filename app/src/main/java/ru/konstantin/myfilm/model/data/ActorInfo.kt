package ru.konstantin.myfilm.model.data

import com.google.gson.annotations.SerializedName

class ActorInfo(
    @field:SerializedName("id") val actorId: String?,
    @field:SerializedName("image") val imageActrorLinkId: String?,
    @field:SerializedName("name") val actorName: String?,
    @field:SerializedName("asCharacter") val actorCharacters: String?
)