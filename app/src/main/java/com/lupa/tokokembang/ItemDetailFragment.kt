package com.lupa.tokokembang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lupa.tokokembang.database.ItemDB
import com.lupa.tokokembang.databinding.FragmentItemDetailBinding

class ItemDetailFragment : Fragment() {
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()
    lateinit var item: ItemDB

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(item: ItemDB) {
        binding.apply {
            itemName.text = item.itemName
            itemSupplier.text = item.itemSupplier
            itemCount.text = item.itemQuantity.toString()
            sellItem.isEnabled = viewModel.isStockAvailable(item)
            sellItem.setOnClickListener { viewModel.sellItem(item) }
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }

    private fun editItem() {
        Navigation.createNavigateOnClickListener(R.id.action_itemDetailFragment_to_addItemFragment)

    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}