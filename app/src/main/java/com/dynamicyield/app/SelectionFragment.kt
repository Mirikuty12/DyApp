package com.dynamicyield.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dynamicyield.app.core.DyWidgets
import com.dynamicyield.app.data.repository.onError
import com.dynamicyield.app.data.repository.onRawError
import com.dynamicyield.app.data.repository.onSuccess
import com.dynamicyield.app.data.source.remote.model.DyWidgetChoice
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.activation.ActivationDialogFragment
import com.dynamicyield.templates.ui.crossupsell.CrossUpsellDialogFragment
import com.dynamicyield.templates.ui.offers.OfferData
import com.dynamicyield.templates.ui.offers.OffersDialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class SelectionFragment : Fragment(R.layout.fragment_selection) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() = with(view) {
        this ?: return@with

        findViewById<MaterialButton>(R.id.cardPromotionBtn).setOnClickListener(selectionListener)
        findViewById<MaterialButton>(R.id.cardPromotionSliderBtn).setOnClickListener(
            selectionListener
        )
        findViewById<MaterialButton>(R.id.quickActionsBtn).setOnClickListener(selectionListener)
        findViewById<MaterialButton>(R.id.quickActionsSliderBtn).setOnClickListener(
            selectionListener
        )
        findViewById<MaterialButton>(R.id.crossUpsellDialogBtn).setOnClickListener(selectionListener)
        findViewById<MaterialButton>(R.id.activationDialogBtn).setOnClickListener(selectionListener)
        findViewById<MaterialButton>(R.id.offersDialogBtn).setOnClickListener(selectionListener)
        findViewById<MaterialButton>(R.id.offersSliderBtn).setOnClickListener(selectionListener)
    }

    private val selectionListener = View.OnClickListener {
        val nextFragment = when (it.id) {
            R.id.cardPromotionBtn -> CardPromotionFragment.newInstance()
            R.id.cardPromotionSliderBtn -> CardPromotionSliderFragment.newInstance()
            R.id.quickActionsBtn -> QuickActionsFragment.newInstance()
            R.id.quickActionsSliderBtn -> QuickActionsSliderFragment.newInstance()
            R.id.crossUpsellDialogBtn -> {
                loadCrossUpsell()
                return@OnClickListener
            }
            R.id.activationDialogBtn -> {
                loadActivation()
                return@OnClickListener
            }
            R.id.offersDialogBtn -> {
                loadOffers()
                return@OnClickListener
            }
            R.id.offersSliderBtn -> OffersSliderFragment.newInstance()
            else -> return@OnClickListener
        }

        activity?.replaceFragment(nextFragment)
    }

    private fun loadCrossUpsell() {
        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.CrossUpsell)
                .onSuccess { choices ->
                    showCrossUpsell(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("SelectionFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("SelectionFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showCrossUpsell(vararg choices: DyWidgetChoice) {
        val crossUpsellChoice =
            choices.find { it.name == DyWidgetName.CrossUpsell.selector } ?: return
        val crossUpsellDialogFragment =
            DyWidgets.createDyWidgetFromChoice<CrossUpsellDialogFragment>(
                requireContext(), crossUpsellChoice
            ) ?: return

        crossUpsellDialogFragment.setListener(
            object : CrossUpsellDialogFragment.CrossUpsellListener {
                override fun onSuccess() {
                    Toast.makeText(context, "Cross&Upsell: success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel(stepIndex: Int) {
                    Toast.makeText(
                        context,
                        "Cross&Upsell: cancel(step=$stepIndex)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
        crossUpsellDialogFragment.show(childFragmentManager, CrossUpsellDialogFragment.TAG)
    }

    private fun loadActivation() {
        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.Activation)
                .onSuccess { choices ->
                    showActivation(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("SelectionFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("SelectionFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showActivation(vararg choices: DyWidgetChoice) {
        val activationChoice = choices.find { it.name == DyWidgetName.Activation.selector } ?: return
        val activationDialogFragment = DyWidgets.createDyWidgetFromChoice<ActivationDialogFragment>(
            requireContext(), activationChoice
        ) ?: return

        activationDialogFragment.setActivationListener(
            object : ActivationDialogFragment.ActivationListener {
                override fun onSuccess() {
                    Toast.makeText(context, "Activation: success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(context, "Activation: cancel", Toast.LENGTH_SHORT).show()
                }
            }
        )
        activationDialogFragment.show(childFragmentManager, ActivationDialogFragment.TAG)
    }

    private fun loadOffers() {
        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.Offers)
                .onSuccess { choices ->
                    showOffers(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("SelectionFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("SelectionFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showOffers(vararg choices: DyWidgetChoice) {
        val offersChoice = choices.find { it.name == DyWidgetName.Offers.selector } ?: return
        val offersDialogFragment = DyWidgets.createDyWidgetFromChoice<OffersDialogFragment>(
            requireContext(), offersChoice
        ) ?: return

        offersDialogFragment.setOffersListener(object : OffersDialogFragment.OffersListener {
            override fun onSuccess(position: Int, offerData: OfferData) {
                Toast.makeText(context, "Offers: success($position)", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(context, "Offers: cancel", Toast.LENGTH_SHORT).show()
            }
        })

        offersDialogFragment.show(childFragmentManager, OffersDialogFragment.TAG)
    }

    companion object {
        fun newInstance() = SelectionFragment()
    }
}