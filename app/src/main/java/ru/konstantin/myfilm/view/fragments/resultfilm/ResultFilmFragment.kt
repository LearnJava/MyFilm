package ru.konstantin.myfilm.view.fragments.resultfilm

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.myfilm.R
import ru.konstantin.myfilm.databinding.FragmentResultFilmBinding
import ru.konstantin.myfilm.model.base.BaseFragment
import ru.konstantin.myfilm.model.data.AppState
import ru.konstantin.myfilm.utils.RESULT_FILM_FRAGMENT_SCOPE
import ru.konstantin.myfilm.utils.convertToColor
import ru.konstantin.myfilm.utils.convertToProgress
import ru.konstantin.myfilm.utils.getStartElementOnPage
import ru.konstantin.myfilm.utils.imageloader.GlideImageLoaderImpl
import ru.konstantin.myfilm.view.fragments.resultfilm.starslist.StarsListRecyclerAdapter
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin

class ResultFilmFragment:
    BaseFragment<FragmentResultFilmBinding>(FragmentResultFilmBinding::inflate) {
    /** Задание переменных */ //region
    // ViewModel
    private lateinit var viewModel: ResultFilmFragmentViewModel
    // ShowResultFilmFragmentScope
    private lateinit var showResultFilmFragmentScope: Scope
    // Навигационные кнопки
    private lateinit var backButton: Button
    private lateinit var searchButton: Button
    // GlideImageLoaderImpl
    private val glideImageLoaderImpl: GlideImageLoaderImpl = getKoin().get()
    // newInstance для данного класса
    companion object {
        fun newInstance(): ResultFilmFragment = ResultFilmFragment()
    }
    //endregion

    /** Работа со Scope */ //region
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Задание Scope для данного фрагмента
        showResultFilmFragmentScope = getKoin().getOrCreateScope(
            RESULT_FILM_FRAGMENT_SCOPE, named(RESULT_FILM_FRAGMENT_SCOPE)
        )
    }
    override fun onDetach() {
        // Удаление скоупа для данного фрагмента
        showResultFilmFragmentScope.close()
        super.onDetach()
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        initViewModel()
        // Инициализация кнопок
        initButtons()
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        val _viewModel: ResultFilmFragmentViewModel by showResultFilmFragmentScope.inject()
        viewModel = _viewModel
        // Подписка на ViewModel
        viewModel.subscribe().observe(viewLifecycleOwner) { renderData(it) }
        // Загрузка данных
        viewModel.getData("", "", "")
    }

    private fun renderData(appState: AppState) {
        if (appState is AppState.SuccessResulFilmInfo) {
            binding.progressbar.visibility = View.INVISIBLE
            binding.contentContainer.visibility = View.VISIBLE
            appState.resultFilmInfo?.let { resultFilmInfo ->
                // Установка фоновой картинки фильма
                val imageLink: String = "${resultFilmInfo.filmImageLink}"
                glideImageLoaderImpl.loadInto(
                    imageLink, binding.contentHeaderFilmBackgroundImage)
                // Установка картинки фильма слева в заголовке
                glideImageLoaderImpl.loadInto(
                    imageLink, binding.contentHeaderFilmImage)
                // Установка названия фильма
                binding.contentHeaderFilmTitle.text = resultFilmInfo.filmTitle
                // Установка даты выхода фильма
                binding.contentHeaderFilmReleasedDate.text = resultFilmInfo.releaseDate
                // Установка длительности фильма
                binding.contentHeaderFilmRuntime.text = resultFilmInfo.filmRunTime
                // Установка жанра(ов) фильма
                binding.contentHeaderFilmGenres.text = resultFilmInfo.filmGenres
                // Установка рейтинга фильма
                val raitinString: String? = resultFilmInfo.filmRating
                val raiting: Int = "${raitinString ?: 0}".convertToProgress()
                binding.contentHeaderFilmRaitingNumber.text = raiting.toString()
                binding.contentHeaderFilmRaitingCircle.progress = raiting
                binding.contentHeaderFilmRaitingCircle.setIndicatorColor(
                    raiting.convertToColor())
                // Установка описания фильма
                binding.contentFooterFilmOverviewText.text = resultFilmInfo.filmOverview
                // Установка списка актёров
                resultFilmInfo.actorList?.let { actorList ->
                    val recyclerView: RecyclerView = binding.contentFooterFilmStarsList
                    recyclerView.layoutManager = LinearLayoutManager(
                        requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    recyclerView.adapter = StarsListRecyclerAdapter(actorList)
                }
            }
        }
        else if (appState is AppState.Loading) {
            binding.progressbar.visibility = View.VISIBLE
            binding.contentContainer.visibility = View.INVISIBLE
        }
        else if (appState is AppState.Error) {
            Toast.makeText(requireContext(), appState.error.message, Toast.LENGTH_SHORT).show()
        }
    }

    // Инициализация кнопок
    private fun initButtons() {
        backButton = binding.backButton
        backButton.setOnClickListener {
            viewModel.router.exit()
        }
        searchButton = binding.searchButton
        backButton.setOnClickListener {
            viewModel.router.navigateTo(viewModel.screens.requestInputScreen())
        }
    }
}