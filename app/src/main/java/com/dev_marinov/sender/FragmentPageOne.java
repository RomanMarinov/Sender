package com.dev_marinov.sender;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev_marinov.sender.databinding.FragmentPageOneDataBinding;


public class FragmentPageOne extends Fragment {

    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private final String SENT = "SMS_SENT"; // отправил
    private final String DELIVERED = "SMS_DELIVERED"; // доставлено
    String phone, message;
    PendingIntent sentPI, deliveredPI; // намерение отправить смс и намерение доставки смс
    BroadcastReceiver smsSentBReceiver, smsDeliveredBReceiver; // класс широковещательных сообщений (отправка и доставка)
    public FragmentPageOneDataBinding fragmentPageOneDataBinding; // имя класса в xml fragment_page_one
    View frag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //frag = inflater.inflate(R.layout.fragment_page_1, container, false);

        sentPI = PendingIntent.getBroadcast(getActivity(), 0, new Intent(SENT),0);
        deliveredPI = PendingIntent.getBroadcast(getActivity(), 0, new Intent(DELIVERED),0);

        fragmentPageOneDataBinding = FragmentPageOneDataBinding.inflate(inflater, container, false);
        frag = fragmentPageOneDataBinding.getRoot();

        fragmentPageOneDataBinding.setNamedatabindingOne(new FragmentPageOneDataBindingView(
                null, null, "send"));
        fragmentPageOneDataBinding.edtPhone.setText("+7("); // доступ к view по одиночке
        fragmentPageOneDataBinding.edtPhone.setSelection(2); // доступ к view по одиночке

        fragmentPageOneDataBinding.edtPhone.addTextChangedListener(textWatcher); // установка edtPhone в textWatcher
        fragmentPageOneDataBinding.edtMessage.addTextChangedListener(textWatcher); // установка edtMessage в textWatcher

        fragmentPageOneDataBinding.btSend.setEnabled(false); // отключение btSend пока не заполнены edtPhone, edtMessage

        fragmentPageOneDataBinding.btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("FRAG_ONE", "fragmentPageOneDataBinding.btSend click");
                phone = fragmentPageOneDataBinding.edtPhone.getText().toString().trim();
                message = fragmentPageOneDataBinding.edtMessage.getText().toString();
                Log.e("FRAG_ONE", "-phone.length-" + phone.length());

                Log.e("FRAG_ONE", "-phone-" + phone + "-message-" + message);


                fragmentPageOneDataBinding.edtMessage.setText(""); // очистка edittext после отправки sms
                // проверка если не подключены права то
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED)
                { // Нет разрешения
                    Log.e("FRAG_ONE", "-checkSelfPermission Нет разрешения-");
                    mPermissionResult.launch(Manifest.permission.SEND_SMS);
                }
                else // Есть разрешения
                {
                    Log.e("FRAG_ONE", "-checkSelfPermission есть разрешение-");
                    sendSMS();
                }
            }

        });

        return frag;
    }


    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(fragmentPageOneDataBinding.edtPhone.getText().toString().length() == 17
            && !fragmentPageOneDataBinding.edtMessage.getText().toString().trim().equals(""))
            {
                fragmentPageOneDataBinding.btSend.setEnabled(true);
            }
            else
            {
                fragmentPageOneDataBinding.btSend.setEnabled(false);
            }
        }
    };

    public void sendSMS()
    {
        Log.e("FRAG_ONE", "-sendSMS СРАБОТАЛ-");
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message, sentPI, deliveredPI);
    }

    @Override
    public void onPause() {
        super.onPause();
        // отключает слушателя в момент сворачивания приложения или закрытия
        getActivity().unregisterReceiver(smsSentBReceiver);
        getActivity().unregisterReceiver(smsDeliveredBReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
// в onResume для запуска BroadcastReceiver после сворачивания, т.к в методе oncreate он не запуститься
        smsSentBReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "СМС отправлена", Toast.LENGTH_SHORT).show();
                        Log.e("FRAG_ONE", "smsSentBReceiver: СМС отправлена");
                        break;

                    case android.telephony.SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "не обслуживается", Toast.LENGTH_SHORT).show();
                        break;

                    case android.telephony.SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "пустой PDU", Toast.LENGTH_SHORT).show();
                        break;

                    case android.telephony.SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Режим полета / радио выключено", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };

        smsDeliveredBReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "СМС доставлена", Toast.LENGTH_SHORT).show();
                        Log.e("FRAG_ONE", "smsDeliveredBReceiver: СМС доставлена");
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "СМС не доставлена", Toast.LENGTH_SHORT).show();
                        Log.e("FRAG_ONE", "smsDeliveredBReceiver: СМС не доставлена");
                        break;
                }
            }
        };

// запуск широковещая сообщений
        getActivity().registerReceiver(smsSentBReceiver, new IntentFilter(SENT));
        getActivity().registerReceiver(smsDeliveredBReceiver, new IntentFilter(DELIVERED));
    }

    // для автотического срабатывания после нажатия кнопки отмена или разрешить
    public ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if(result) {
                        Log.e("FRAG_ONE", "onActivityResult: PERMISSION GRANTED");
                        sendSMS();
                    } else {
                        Log.e("FRAG_ONE", "onActivityResult: PERMISSION DENIED");
                    }
                }
            });

}