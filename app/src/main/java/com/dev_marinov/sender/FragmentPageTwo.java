package com.dev_marinov.sender;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.dev_marinov.sender.databinding.FragmentPageTwoDataBinding;
import com.dev_marinov.sender.databinding.FragmentPageTwoDataBindingImpl;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class FragmentPageTwo extends Fragment {

    public FragmentPageTwoDataBinding fragmentPageTwoDataBinding;
    View frag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     //   return inflater.inflate(R.layout.fragment_page_two, container, false);

        fragmentPageTwoDataBinding = FragmentPageTwoDataBinding.inflate(inflater, container, false);
        frag = fragmentPageTwoDataBinding.getRoot();

        fragmentPageTwoDataBinding.setDatabindingviewTwo(new FragmentPageTwoDataBindingView("email to", null,
                "subject", null, "message", null, "send"));

        fragmentPageTwoDataBinding.edtEmailAddress.addTextChangedListener(textWatcher);
        fragmentPageTwoDataBinding.edtMessage.addTextChangedListener(textWatcher);
        fragmentPageTwoDataBinding.btSendEmail.setEnabled(false);

        fragmentPageTwoDataBinding.btSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("FRAG_TWO", "btSendEmail click-");

                RequestParams requestParams = new RequestParams();
                requestParams.put("cmd", "registration");
                requestParams.put("EmailAddress", fragmentPageTwoDataBinding.edtEmailAddress.getText().toString());
                requestParams.put("Subject", fragmentPageTwoDataBinding.edtSubject.getText().toString().trim());
                requestParams.put("Message", fragmentPageTwoDataBinding.edtMessage.getText().toString());

                Log.e("FRAG_TWO", "edtEmailAddress-" + fragmentPageTwoDataBinding.edtEmailAddress.getText().toString());
                Log.e("FRAG_TWO", "edtMessage-" + fragmentPageTwoDataBinding.edtMessage.getText().toString());

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.post("https://dev-marinov.ru/server/sender_server/srRegistration.php", requestParams, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.e("FRAG_TWO", "onFailure-respone-" + responseString);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        Log.e("FRAG_TWO","onSuccess responseString : "+responseString);
                        JSONObject jsonObject_1 = null;
                        try {
                            jsonObject_1 = new JSONObject(responseString);
                            String cmd = jsonObject_1.getString("cmd");
                            Log.e("FRAG_TWO ","-respone-" + responseString);

                            if (cmd.equals("reg")) { // успешно записан

                                Toast.makeText(getActivity(), "письмо отпралено на почту", Toast.LENGTH_SHORT).show();

                            }
                            if (cmd.equals("error")) { // не записано

                                Toast.makeText(getActivity(), "письмо НЕ отпралено на почту", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (JSONException e) {
                            Log.e("Ошибка при разборе","Отчет: "+e);
                            e.printStackTrace();
                        }
                    }
                });
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
            // проверяем если в edtEmailAddress символы '@' и '.', а так же не пустой ли edtMessage
            // тогда открывает кнопку отправки сообщения
            if(fragmentPageTwoDataBinding.edtEmailAddress.getText().toString().indexOf('@') != -1
                    && fragmentPageTwoDataBinding.edtEmailAddress.getText().toString().indexOf('.') != -1
                    && !fragmentPageTwoDataBinding.edtMessage.getText().toString().trim().equals(""))
            {
                fragmentPageTwoDataBinding.btSendEmail.setEnabled(true);
            }
            else
            {
                fragmentPageTwoDataBinding.btSendEmail.setEnabled(false);
            }

        }
    };

}