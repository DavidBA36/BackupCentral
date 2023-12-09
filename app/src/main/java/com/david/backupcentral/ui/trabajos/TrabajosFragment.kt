package com.david.backupcentral.ui.trabajos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david.backupcentral.R
import com.david.backupcentral.adapters.RecyclerAdapter
import com.david.backupcentral.databinding.FragmentTrabajosBinding
import com.david.backupcentral.listeners.SnackbarListener
import com.david.backupcentral.models.ModelItem
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.CopyOnWriteArrayList

class TrabajosFragment : Fragment() {

    private var _binding: FragmentTrabajosBinding? = null
    private val binding get() = _binding!!
    private val componentes:MutableList<ModelItem> = CopyOnWriteArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(TrabajosViewModel::class.java)

        _binding = FragmentTrabajosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        componentes.add(ModelItem(5, 6, "hola"))
        val recycler = binding.rvTrabajos
        val recadapter = RecyclerAdapter(componentes)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = recadapter

        val mySnackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), "Prueba", Snackbar.LENGTH_SHORT)
        mySnackbar.setAction("undo", SnackbarListener())
        mySnackbar.show()

       // val textView: TextView = binding.textHome


        /*homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}