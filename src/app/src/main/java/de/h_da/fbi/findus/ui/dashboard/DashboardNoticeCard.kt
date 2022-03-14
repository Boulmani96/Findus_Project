package de.h_da.fbi.findus.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import de.h_da.fbi.common.entity.ApiPatient
import de.h_da.fbi.common.entity.PatientUpdate
import de.h_da.fbi.common.repository.FindusRepository
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.ui.patientsList
import de.h_da.fbi.findus.ui.selectedPatient
import kotlinx.coroutines.launch

var MAXHEIGHT_DASHBOADRD_NOTICECARD_COLUMN = 0.5F
var MAXWIDTH_DASHBOADRD_NOTICECARD_TXT = 0.8F

/**
 * Shows the diagnostic notes of the selected patient
 * Notes can be edited/added/deleted, these are stored in the database
 */
@Composable
fun NoticeCard() {
    val newMutableListNotices = remember { mutableStateOf(selectedPatient!!.diagnosticNotes.toMutableList()) }
    newMutableListNotices.value = selectedPatient!!.diagnosticNotes.toMutableList()
    val coroutineScope = rememberCoroutineScope()
    enabeldAddButton = textFieldstats != ""
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.all_padding))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.all_padding))
    ){
        Column(
            Modifier
                .fillMaxHeight(MAXHEIGHT_DASHBOADRD_NOTICECARD_COLUMN)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_vertical_arrangement_dashboard_notice_card))
        ) {
            newMutableListNotices.component1().forEach { index ->
                Card(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.all_padding_small_dashboard))
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {
                    Row(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.all_padding_small_dashboard)),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = index,
                            fontSize = dimensionResource(id = R.dimen.all_font_size_txt_dashboard_small).value.sp,
                            modifier = Modifier
                                .padding(start = dimensionResource(id = R.dimen.all_padding))
                                .fillMaxWidth(MAXWIDTH_DASHBOADRD_NOTICECARD_TXT)
                        )
                        IconButton(
                            onClick = {
                                newMutableListNotices.value.remove(index)
                                coroutineScope.launch {
                                    FindusRepository().patchPatient(selectedPatient!!.id,
                                        PatientUpdate(null, null, null, null, null,
                                            null, null, null, null,
                                            diagnosticNotes = newMutableListNotices.component1()))
                                    selectedPatient = FindusRepository().fetchPatient(selectedPatient!!.id)
                                    patientsList = FindusRepository().fetchPatients() as MutableList<ApiPatient>
                                }
                                textFieldstats = index
                            }
                        ) {
                            Icon(
                                Icons.Filled.Edit, stringResource(id = R.string.dashboard_description_edit),
                                modifier = Modifier
                                    .offset(x = dimensionResource(id = R.dimen.offset_edit_icon_dashboard_notice_card))
                                    .size(dimensionResource(id = R.dimen.size_icon_dashboard_notice_card))
                            )
                        }
                        IconButton(
                            onClick = {
                                newMutableListNotices.value.remove(index)
                                coroutineScope.launch {
                                    FindusRepository().patchPatient(selectedPatient!!.id,
                                        PatientUpdate(null, null, null, null, null,
                                            null, null, null, null,
                                            diagnosticNotes = newMutableListNotices.component1()))
                                    selectedPatient = FindusRepository().fetchPatient(selectedPatient!!.id)
                                    patientsList = FindusRepository().fetchPatients() as MutableList<ApiPatient>
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.Delete, " ",
                                modifier = Modifier
                                    .offset(x = dimensionResource(id = R.dimen.offset_delete_icon_dashboard_notice_card))
                                    .size(dimensionResource(id = R.dimen.size_icon_dashboard_notice_card))
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom) {
            TextField(
                value = textFieldstats,
                onValueChange = { textFieldstats = it },
                trailingIcon = {
                    IconButton(
                        onClick = { textFieldstats = "" },
                        enabled = enabeldAddButton
                    ) {
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = stringResource(id = R.string.dashboard_description_delete),
                            modifier = Modifier.offset(x = dimensionResource(id = R.dimen.offset_clear_icon_dashboard_notice_card))
                        )
                    } },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_vertical_arrangement_dashboard_notice_card))
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFFFFFFF),
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                ),
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.all_font_size_txt_dashboard_small).value.sp
                ),
                placeholder = { Text(text = stringResource(id = R.string.dashboard_placeholder_enter_notes), fontSize = dimensionResource(id = R.dimen.all_font_size_txt_dashboard_small).value.sp) }
            )
            Button(
                onClick = {
                    newMutableListNotices.value.add(textFieldstats)
                    coroutineScope.launch {
                        FindusRepository().patchPatient(selectedPatient!!.id,
                            PatientUpdate(null, null, null, null, null,
                                null, null, null, null,
                                diagnosticNotes = newMutableListNotices.component1()))
                        selectedPatient = FindusRepository().fetchPatient(selectedPatient!!.id)
                        patientsList = FindusRepository().fetchPatients() as MutableList<ApiPatient>
                    }
                    textFieldstats = ""
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.all_padding_small_dashboard)),
                enabled = enabeldAddButton
            )
            {
                Text(text = stringResource(id = R.string.dashboard_btn_add_notes), fontSize = dimensionResource(id = R.dimen.all_font_size_txt_dashboard_small).value.sp)
            }
        }
    }
}
