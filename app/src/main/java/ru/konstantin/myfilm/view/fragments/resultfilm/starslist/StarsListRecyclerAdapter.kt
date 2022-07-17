package ru.konstantin.myfilm.view.fragments.resultfilm.starslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.myfilm.R
import ru.konstantin.myfilm.model.data.ActorInfo
import ru.konstantin.myfilm.utils.imageloader.GlideImageLoaderImpl
import org.koin.java.KoinJavaComponent

class StarsListRecyclerAdapter(private val starsList: List<ActorInfo>):
    RecyclerView.Adapter<StarsListRecyclerAdapter.MyViewHolder>() {
    /** Исходные данные */ //region
    // GlideImageLoaderImpl
    private val glideImageLoaderImpl: GlideImageLoaderImpl = KoinJavaComponent.getKoin().get()
    //endregion

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val starImage: ImageView = itemView.findViewById(R.id.star_image)
        val starName: TextView = itemView.findViewById(R.id.star_name)
        val starCharacter: TextView = itemView.findViewById(R.id.star_character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).
            inflate(R.layout.stars_list_recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Установка фотографии актёра
        val imageLink: String = "${starsList[position].imageActrorLinkId}"
        glideImageLoaderImpl.loadInto(imageLink, holder.starImage)
        // Установка имени актёра
        holder.starName.text = starsList[position].actorName
        // Установка роли актёра
        holder.starCharacter.text = starsList[position].actorCharacters
    }

    override fun getItemCount() = starsList.size
}