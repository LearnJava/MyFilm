package ru.konstantin.myfilm.view.fragments.resultpages

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.progressindicator.CircularProgressIndicator
import ru.konstantin.myfilm.databinding.FragmentResultPagesBinding
import ru.konstantin.myfilm.model.base.BaseFragment
import ru.konstantin.myfilm.model.data.AppState
import ru.konstantin.myfilm.model.data.GeneralFilmInfo
import ru.konstantin.myfilm.repository.settings.Settings
import ru.konstantin.myfilm.utils.*
import ru.konstantin.myfilm.utils.imageloader.GlideImageLoaderImpl
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin

class ResultPagesFragment:
    BaseFragment<FragmentResultPagesBinding>(FragmentResultPagesBinding::inflate) {
    /** Задание переменных */ //region
    // ViewModel
    private lateinit var viewModel: ResultPagesFragmentViewModel
    // ShowResultPagesFragmentScope
    private lateinit var showResultPagesFragmentScope: Scope
    // Класс для сохранения запроса
    private val settings: Settings = getKoin().get()
    // Навигационные кнопки
    private lateinit var backButton: Button
    private lateinit var searchButton: Button
    // Индикаторы загрузки
    private lateinit var progressBarsList: List<ProgressBar>
    // Контейнеры для картинок
    private lateinit var imageViewList: List<ImageView>
    // Названия найденных фильмов
    private lateinit var filmsTitles: List<TextView>
    // Даты найденных фильмов
    private lateinit var filmsDates: List<TextView>
    // Рейтинги найденных фильмов
    private lateinit var filmsRaitings: List<CircularProgressIndicator>
    private lateinit var filmsRaitingsNumbers: List<TextView>
    // GlideImageLoaderImpl
    private val glideImageLoaderImpl: GlideImageLoaderImpl = getKoin().get()
    // Контейнеры с найденными фильмами
    private lateinit var filmsResults: List<ConstraintLayout>
    // Элементы управления пагинацией
    private lateinit var startButton: Button
    private lateinit var previousButton: Button
    private lateinit var followingButton: Button
    private lateinit var endButton: Button
    private lateinit var buttonsWithNumbers: List<TextView>

    // newInstance для данного класса
    companion object {
        fun newInstance(): ResultPagesFragment = ResultPagesFragment()
    }
    //endregion

    /** Работа со Scope */ //region
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Задание Scope для данного фрагмента
        showResultPagesFragmentScope = getKoin().getOrCreateScope(
            RESULT_PAGES_FRAGMENT_SCOPE, named(RESULT_PAGES_FRAGMENT_SCOPE)
        )
    }
    override fun onDetach() {
        // Удаление скоупа для данного фрагмента
        showResultPagesFragmentScope.close()
        super.onDetach()
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        initViewModel()
        // Инициализация кнопок
        initButtons()
        // Инициализация индикаторов загрузки
        initProgressBars()
        // Инициализация контейнеров для картинок
        initImageViews()
        // Инициализация названий найденных фильмов
        initFilmsTitles()
        // Инициализация дат найденных фильмов
        initFilmsDates()
        // Инициализация рейтингов найденных фильмов
        initFilmsRaitings()
        // Инициализация контейнеров с найденными фильмами
        initFilmsResults()
        // Инициализация элементов управления пагинацией
        initPaginationElements()
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        val _viewModel: ResultPagesFragmentViewModel by showResultPagesFragmentScope.inject()
        viewModel = _viewModel
        // Подписка на ViewModel
        viewModel.subscribe().observe(viewLifecycleOwner) { renderData(it) }
        // Загрузка данных
        viewModel.getData("", "", "")
    }

    private fun renderData(appState: AppState) {
        if (appState is AppState.SuccessListFilmsInfo) {
            appState.generalFilmInfoList?.let { generalFilmInfoList ->
                showFoundedFilmsList(generalFilmInfoList)
            }
        }
        else if (appState is AppState.Loading) {
            progressBarsList.forEach { it.visibility = View.VISIBLE }
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

    // Инициализация индикаторов загрузки
    private fun initProgressBars() {
        progressBarsList = listOf(
            binding.contentStartTopFilmImageProgressbar,
            binding.contentEndTopFilmImageProgressbar,
            binding.contentStartBottomFilmImageProgressbar,
            binding.contentEndBottomFilmImageProgressbar
        )
        progressBarsList.forEach { it.visibility = View.INVISIBLE }
    }

    // Инициализация контейнеров для картинок
    private fun initImageViews() {
        imageViewList = listOf(
            binding.contentStartTopFilmImage,
            binding.contentEndTopFilmImage,
            binding.contentStartBottomFilmImage,
            binding.contentEndBottomFilmImage
        )
    }

    // Инициализация названий найденных фильмов
    private fun initFilmsTitles() {
        filmsTitles = listOf(
            binding.contentStartTopFilmTitle,
            binding.contentEndTopFilmTitle,
            binding.contentStartBottomFilmTitle,
            binding.contentEndBottomFilmTitle
        )
    }

    // Инициализация дат найденных фильмов
    private fun initFilmsDates() {
        filmsDates = listOf(
            binding.contentStartTopFilmDate,
            binding.contentEndTopFilmDate,
            binding.contentStartBottomFilmDate,
            binding.contentEndBottomFilmDate
        )
    }

    // Инициализация рейтингов найденных фильмов
    private fun initFilmsRaitings() {
        filmsRaitings = listOf(
            binding.contentStartTopFilmRaitingCircle,
            binding.contentEndTopFilmRaitingCircle,
            binding.contentStartBottomFilmRaitingCircle,
            binding.contentEndBottomFilmRaitingCircle
        )

        filmsRaitingsNumbers = listOf(
            binding.contentStartTopFilmRaitingNumber,
            binding.contentEndTopFilmRaitingNumber,
            binding.contentStartBottomFilmRaitingNumber,
            binding.contentEndBottomFilmRaitingNumber
        )
    }

    // Инициализация контейнеров с найденными фильмами
    private fun initFilmsResults() {
        filmsResults = listOf(
            binding.contentStartTop,
            binding.contentEndTop,
            binding.contentStartBottom,
            binding.contentEndBottom
        )
        repeat(MAX_NUMBER_FILM_RESULTS_ON_SCREEN) { index->
            filmsResults[index].setOnClickListener {
                val resultIndex: Int =
                    settings.pagingNumber * MAX_NUMBER_FILM_RESULTS_ON_SCREEN + index
                if (settings.advancedSearchResult.size > resultIndex)
                    settings.idChoosedFilm = "${settings.advancedSearchResult[resultIndex].filmId}"
                // Загрузка фрагмента с детальной информацией о выбранном фильме
                viewModel.router.navigateTo(viewModel.screens.resultFilmScreen())
            }
        }
    }

    // Инициализация элементов управления пагинацией
    @SuppressLint("SetTextI18n")
    private fun initPaginationElements() {
        buttonsWithNumbers = listOf(
            binding.footerFirstElement,
            binding.footerSecondElement,
            binding.footerThirdElement,
            binding.footerFourthElement,
            binding.footerFifthElement,
            binding.footerSixthElement,
            binding.footerSeventhElement,
            binding.footerEighthElement,
            binding.footerNinthElement
        )
        buttonsWithNumbers[0].textSize = TEXT_SIZE_MARKED_NUMBER
        val middleIndex: Int = buttonsWithNumbers.size / 2
        val maxIndex: Int = settings.advancedSearchResult.size /
                MAX_NUMBER_FILM_RESULTS_ON_SCREEN
        buttonsWithNumbers.forEachIndexed { index, currentButton->
            currentButton.setOnClickListener {
                buttonsWithNumbers.forEach { numberButton->
                    numberButton.textSize = TEXT_SIZE_NOT_MARKED_NUMBER
                }

                if (index < middleIndex) {
                    if (settings.pagingNumber <= middleIndex) {
                        settings.pagingNumber = index
                        buttonsWithNumbers.forEachIndexed { correctingIndex, numberButton ->
                            numberButton.text = "${correctingIndex + 1}"
                        }
                        currentButton.textSize = TEXT_SIZE_MARKED_NUMBER
                    } else {
                        settings.pagingNumber -= middleIndex - index
                        if (settings.pagingNumber > middleIndex) {
                            buttonsWithNumbers.forEachIndexed { correctingIndex, numberButton ->
                                numberButton.text =
                                    "${settings.pagingNumber - middleIndex + correctingIndex}"
                                if (correctingIndex == middleIndex)
                                    numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                            }
                        } else {
                            buttonsWithNumbers.forEachIndexed { correctingIndex, numberButton ->
                                numberButton.text = "${correctingIndex + 1}"
                                if (correctingIndex == middleIndex)
                                    numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                            }
                        }
                    }
                } else if (index == middleIndex) {
                    settings.pagingNumber = index
                    currentButton.textSize = TEXT_SIZE_MARKED_NUMBER
                } else if (index > middleIndex) {
                    settings.pagingNumber = currentButton.text.toString().toInt() - 1
                    buttonsWithNumbers.forEachIndexed { correctingIndex, numberButton ->
                        if (maxIndex - settings.pagingNumber > middleIndex ) {
                            numberButton.text =
                                "${settings.pagingNumber + 1 - middleIndex + correctingIndex}"
                            if (correctingIndex == middleIndex)
                                numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                        } else {
                            if (correctingIndex == index)
                                numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                        }
                    }
                }
                // Обновление списка найденных фильмов
                showFoundedFilmsList(settings.advancedSearchResult)
            }
        }

        startButton = binding.footerPreviousStartButton
        startButton.setOnClickListener {
            if (settings.pagingNumber != 0) {
                settings.pagingNumber = 0
                showFoundedFilmsList(settings.advancedSearchResult)
                buttonsWithNumbers.forEachIndexed { correctingIndex, numberButton ->
                    numberButton.text = "${correctingIndex + 1}"
                    if (correctingIndex != 0) numberButton.textSize = TEXT_SIZE_NOT_MARKED_NUMBER
                }
                buttonsWithNumbers[0].textSize = TEXT_SIZE_MARKED_NUMBER
            }
        }
        previousButton = binding.footerPreviousButton
        previousButton.setOnClickListener {
            if (settings.pagingNumber > 0) {
                settings.pagingNumber--
                showFoundedFilmsList(settings.advancedSearchResult)
                buttonsWithNumbers.forEachIndexed { correctingIndex, numberButton ->
                    if (settings.pagingNumber >= middleIndex) {
                        val curNumberValue: Int = numberButton.text.toString().toInt()
                        if (curNumberValue > 1) numberButton.text = "${curNumberValue - 1}"
                        if (correctingIndex == middleIndex)
                            numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                        else numberButton.textSize = TEXT_SIZE_NOT_MARKED_NUMBER
                    } else {
                        if (correctingIndex == settings.pagingNumber)
                            numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                        else numberButton.textSize = TEXT_SIZE_NOT_MARKED_NUMBER
                    }
                }
            }
        }
        followingButton = binding.footerFollowingButton
        followingButton.setOnClickListener {
            if (settings.pagingNumber + 1 <= maxIndex) {
                settings.pagingNumber++
                showFoundedFilmsList(settings.advancedSearchResult)
                buttonsWithNumbers.forEachIndexed { correctingIndex, numberButton ->
                    if (settings.pagingNumber <= maxIndex - middleIndex) {
                        val curNumberValue: Int = numberButton.text.toString().toInt()
                        if (curNumberValue < settings.advancedSearchResult.size /
                            MAX_NUMBER_FILM_RESULTS_ON_SCREEN)
                                numberButton.text = "${curNumberValue + 1}"
                        if (correctingIndex == middleIndex)
                            numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                        else numberButton.textSize = TEXT_SIZE_NOT_MARKED_NUMBER
                    } else {
                        if (correctingIndex ==
                            buttonsWithNumbers.size - 1 - maxIndex + settings.pagingNumber)
                            numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                        else numberButton.textSize = TEXT_SIZE_NOT_MARKED_NUMBER
                    }
                }
            }
        }
        endButton = binding.footerFollowingEndButton
        endButton.setOnClickListener {
            if (settings.pagingNumber !=
                settings.advancedSearchResult.size / MAX_NUMBER_FILM_RESULTS_ON_SCREEN) {
                settings.pagingNumber =
                    settings.advancedSearchResult.size / MAX_NUMBER_FILM_RESULTS_ON_SCREEN
                showFoundedFilmsList(settings.advancedSearchResult)
                buttonsWithNumbers.forEachIndexed { correctingIndex, numberButton ->
                    numberButton.text =
                        "${settings.pagingNumber - buttonsWithNumbers.size + 1 + correctingIndex}"
                    if (correctingIndex != buttonsWithNumbers.size - 1)
                        numberButton.textSize = TEXT_SIZE_NOT_MARKED_NUMBER
                    else numberButton.textSize = TEXT_SIZE_MARKED_NUMBER
                }
            }
        }
    }

    // Отображение списка найденных фильмов
    private fun showFoundedFilmsList(generalFilmInfoList: List<GeneralFilmInfo>) {
        progressBarsList.forEach { it.visibility = View.VISIBLE }
        // Скрытие контейнеров для вставки данных о фильмах
        filmsResults.forEach { it.visibility = View.INVISIBLE }
        repeat(MAX_NUMBER_FILM_RESULTS_ON_SCREEN) {
            if (generalFilmInfoList.size >
                settings.pagingNumber.getStartElementOnPage() + it) {
                // Отображение контейнеров и скрытие прогресс-баров для вставки данных о фильмах
                filmsResults[it].visibility = View.VISIBLE
                progressBarsList[it].visibility = View.INVISIBLE
                // Установка картинок на найденные фильмы
                val imageLink: String = "${generalFilmInfoList[settings.pagingNumber.
                getStartElementOnPage() + it].filmImageLink}"
                glideImageLoaderImpl.loadInto(imageLink, imageViewList[it])
                // Установка названий найденных фильмов
                filmsTitles[it].text = "${generalFilmInfoList[settings.pagingNumber.
                getStartElementOnPage() + it].filmTitle}"
                // Установка дат найденных фильмов
                filmsDates[it].text = "${generalFilmInfoList[settings.pagingNumber.
                getStartElementOnPage() + it].filmData}".deleteBrackets()
                // Установка рейтингов найденных фильмов
                val raitinString: String? = generalFilmInfoList[settings.pagingNumber.
                getStartElementOnPage() + it].filmRating
                val raiting: Int = "${raitinString ?: 0}".convertToProgress()
                filmsRaitings[it].progress = raiting
                filmsRaitings[it].setIndicatorColor(raiting.convertToColor())
                filmsRaitingsNumbers[it].text = "$raiting"
            }
        }
    }

    // Отображение новых номеров внизу страницы,
    // соответствующих текущему просматриваему списку найденных фильмов
    private fun showPagingNumber() {

    }
}