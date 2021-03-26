package com.csosa.fntest.viewmodels

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csosa.fntest.BaseViewModelTest
import com.csosa.fntest.fakes.FakeGetAllVehiclesUseCase
import com.csosa.fntest.utils.UiState
import com.csosa.fntest.viewmodel.ListVehiclesViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE, sdk = [Build.VERSION_CODES.P])
internal class ListVehiclesViewModelTest : BaseViewModelTest() {

    // region Members

    private lateinit var listVehiclesViewModel: ListVehiclesViewModel

    // endregion

    // region Tests

    @Test
    fun `should get all vehicles`() {
        coroutineTestRule.dispatcher.runBlockingTest {

            prepareViewModel(UiState.SUCCESS)
            listVehiclesViewModel.executeGetAllVehicles("")

            listVehiclesViewModel.vehiclesViewState.observeForever { state ->

                if (state.isLoading) {

                    Truth.assertThat(state.vehicles).isNull()
                    Truth.assertThat(state.error).isNull()
                } else {

                    Truth.assertThat(state.error).isNull()
                    Truth.assertThat(state.vehicles?.size).isEqualTo(3)
                }
            }
        }
    }

    // endregion

    // region BaseViewModelTest

    override fun prepareViewModel(uiState: UiState) {

        val getAllVehiclesUseCase = FakeGetAllVehiclesUseCase(uiState)

        listVehiclesViewModel = ListVehiclesViewModel(getAllVehiclesUseCase)

    }

    // endregion

}
