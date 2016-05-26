package com.nu3.views;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nu3.R;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonateFragment extends Fragment {


    private static final String TAG = "paymentExample";
    /**
     * - Set to PayPalConfiguration.ENVIRONMENT_PRODUCTION to move real money.
     *
     * - Set to PayPalConfiguration.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     *
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "ASAItZI6P5k-AxQYSCd4NnrDTivhOvyajuKHiq_v7sHe3SCdSpFFUnrGQdo1Q8rwvyF6sHgP6jzzGM-h";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Fundacion Nu3")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.nu3.co"))
            .merchantUserAgreementUri(Uri.parse("https://www.nu3.co"));

    PayPalPayment thingToBuy;

    EditText amountField;

    Snackbar resultSnackbar;

    View rootView;


    public DonateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_donate, container, false);
        Intent intent = new Intent(getContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);

        amountField = (EditText) rootView.findViewById(R.id.input_amount_donation);

        rootView.findViewById(R.id.single_payment_paypal_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = String.valueOf(amountField.getText());

                if (!amount.isEmpty()) {
                    String foundationNameDonation = Locale.getDefault().getLanguage().equals("es") ? "Donación Fundación nu3" : "nu3 Foundation donation";
                    thingToBuy = new PayPalPayment(new BigDecimal(amount), "USD",
                            foundationNameDonation, PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(getContext(),
                            PaymentActivity.class);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
            }
        });

        rootView.findViewById(R.id.pse_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WebViewActivity.class));
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        String payment = confirm.getPayment().toJSONObject().toString(4);
                        resultSnackbar = Snackbar.make(rootView, R.string.donate_result_ok, Snackbar.LENGTH_LONG);
                    } catch (JSONException e) {
                        resultSnackbar = Snackbar.make(rootView, R.string.donate_result_error, Snackbar.LENGTH_LONG);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                resultSnackbar = Snackbar.make(rootView, R.string.donate_result_cancelled, Snackbar.LENGTH_LONG);
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                resultSnackbar = Snackbar.make(rootView, R.string.donate_result_invalid, Snackbar.LENGTH_LONG);
            }
        }
        resultSnackbar.show();
    }

}
