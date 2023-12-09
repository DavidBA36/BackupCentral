package com.david.backupcentral.helpers

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar


class DatePickerFragment: DialogFragment() {
    //debemos crear el listener en la clase que invocará el dialog
    private var listener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        //creamos la instancia para aplicar los limites y la devolvemos

        val datePickerDialog = DatePickerDialog(requireActivity(), listener, year, month, day)

        c.set(Calendar.DAY_OF_MONTH, day+1)
        datePickerDialog.datePicker.minDate = c.timeInMillis

        return datePickerDialog
    }

   /*Para pasar el listener como parámetro a nuestro DialogFragment vamos a definir un método newInstance,
    que será el encargado de recibirlo*/

    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.listener = listener
            return fragment
        }
    }

}