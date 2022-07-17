package ru.konstantin.myfilm.view.fragments.requestinput

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import ru.konstantin.myfilm.databinding.FragmentRequestInputBinding
import ru.konstantin.myfilm.model.base.BaseFragment
import ru.konstantin.myfilm.model.data.AppState
import ru.konstantin.myfilm.utils.REQUEST_INPUT_FRAGMENT_SCOPE
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent
import androidx.lifecycle.Observer
import ru.konstantin.myfilm.repository.settings.Settings
import ru.konstantin.myfilm.utils.convertToInquiry

class RequestInputFragment:
    BaseFragment<FragmentRequestInputBinding>(FragmentRequestInputBinding::inflate) {
    /** Задание переменных */ //region
    // ViewModel
    private lateinit var viewModel: RequestInputFragmentViewModel
    // ShowRequestInputFragmentScope
    private lateinit var showRequestInputFragmentScope: Scope
    // Поля для ввода запроса с информацией о фильмах
    private lateinit var titleSearchField: EditText
    private lateinit var titleTypeSearchField: Spinner
    private lateinit var genresSearchField: Spinner
    // Кнопка для отправки запроса с информацией о фильмах
    private lateinit var searchButton: Button
    // Класс для сохранения запроса
    private val settings: Settings = KoinJavaComponent.getKoin().get()
    // newInstance для данного класса
    companion object {
        fun newInstance(): RequestInputFragment = RequestInputFragment()
    }
    //endregion

    /** Работа со Scope */ //region
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Задание Scope для данного фрагмента
        showRequestInputFragmentScope = KoinJavaComponent.getKoin().getOrCreateScope(
            REQUEST_INPUT_FRAGMENT_SCOPE, named(REQUEST_INPUT_FRAGMENT_SCOPE)
        )
    }
    override fun onDetach() {
        // Удаление скоупа для данного фрагмента
        showRequestInputFragmentScope.close()
        super.onDetach()
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        initViewModel()
        // Инициализация полей ввода исходной информации и кнопки для отправки запроса
        initFieldsAndSearchButton()
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        val _viewModel: RequestInputFragmentViewModel by showRequestInputFragmentScope.inject()
        viewModel = _viewModel
        // Подписка на ViewModel
        this.viewModel.subscribe().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(appState: AppState) {
        if (appState is AppState.Success) {
            viewModel.router.navigateTo(viewModel.screens.resultPagesScreen())
        }
        else if (appState is AppState.Loading) {
            // Изменение внешнего вида фрагмента
            binding.requestElementsGroup.visibility = View.INVISIBLE
            binding.progressbar.visibility = View.VISIBLE
        }
        else if (appState is AppState.Error) {
            Toast.makeText(requireContext(), appState.error.message, Toast.LENGTH_SHORT).show()
        }
    }

    // Инициализация полей ввода исходной информации и кнопки для отправки запроса
    private fun initFieldsAndSearchButton() {
        titleSearchField = binding.filmTitleInputField
        titleSearchField.doOnTextChanged { _, _, _, _ ->
            settings.filmTitle = titleSearchField.text.toString()
        }
        titleTypeSearchField = binding.filmTitleTypesList.also { it.setSelection(0)}

        titleTypeSearchField.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            var selectCounter: Int = 0
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long,
            ) {
                // Действие, в случае выбора элемента списка
                if ((selectCounter++ > 0) && (position > 0)) {
                    settings.filmTitleType =
                        titleTypeSearchField.selectedItem.toString().convertToInquiry()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        genresSearchField = binding.filmGenresList.also { it.setSelection(0)}
        genresSearchField.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            var selectCounter: Int = 0
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long,
            ) {
                // Действие, в случае выбора элемента списка
                if ((selectCounter++ > 0) && (position > 0)) {
                    settings.filmGenre =
                        genresSearchField.selectedItem.toString().convertToInquiry()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        searchButton = binding.runSearchButton
        searchButton.setOnClickListener {
            // Запуск поиска
            viewModel.getData(
                titleSearchField.text.toString(),
                titleTypeSearchField.selectedItem.toString().convertToInquiry(),
                genresSearchField.selectedItem.toString().convertToInquiry()
            )
        }
    }
}