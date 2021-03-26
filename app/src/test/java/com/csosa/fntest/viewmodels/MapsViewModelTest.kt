package com.csosa.fntest.viewmodels

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csosa.fntest.BaseViewModelTest
import com.csosa.fntest.domain.model.Bounds
import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.fakes.FakeGetAllVehiclesUseCase
import com.csosa.fntest.mappers.toPresentation
import com.csosa.fntest.utils.Data
import com.csosa.fntest.utils.UiState
import com.csosa.fntest.viewmodel.MapsViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE, sdk = [Build.VERSION_CODES.P])
internal class MapsViewModelTest : BaseViewModelTest() {

    // region Members

    private lateinit var mapsViewModel: MapsViewModel

    // endregion

    // region Tests

    @Test
    fun `should get all vehicles`() {
        coroutineTestRule.dispatcher.runBlockingTest {

            prepareViewModel(UiState.SUCCESS)
            mapsViewModel.executeGetVehiclesByBounds("", Bounds(Coordinate(0.0, 0.0), Coordinate(0.0, 0.0)), Coordinate(0.0, 0.0))

            mapsViewModel.mapsViewState.observeForever { state ->

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



    @Test
    fun `should update selected vehicle`() {
        coroutineTestRule.dispatcher.runBlockingTest {

            prepareViewModel(UiState.SUCCESS)

            mapsViewModel.updateSelected(Data.vehicles[0].toPresentation())

            mapsViewModel.mapsViewState.observeForever { state ->

                if (state.isLoading) {

                    Truth.assertThat(state.vehicles).isNull()
                    Truth.assertThat(state.error).isNull()
                } else {

                    Truth.assertThat(state.error).isNull()
                    Truth.assertThat(state.selected).isNotNull()
                    Truth.assertThat(state.selected?.id).isEqualTo(Data.vehicles[0].id)

                }
            }
        }
    }


    // endregion

    // region BaseViewModelTest

    override fun prepareViewModel(uiState: UiState) {

        val getAllVehiclesUseCase = FakeGetAllVehiclesUseCase(uiState)

        mapsViewModel = MapsViewModel(getAllVehiclesUseCase)

    }

    // endregion

}
