package com.example.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Entity.QuestionItem;
import com.example.myapplication.R;

public class SurveyFragment extends Fragment {
    private static final String KEY_QUESTION = "key_question_item";
    private static final String KEY_NUMBER = "key_number";
    private static final String KEY_LOWER_FACTOR_LABEL = "key_lower_factor_label";
    private static final String KEY_DEBUG = "key_debug";

    private OnFragmentInteractionListener mListener;

    private QuestionItem mQuestionItem;
    private int mPickupCount;
    private String mLowerFactorLabel;
    private String mDebugMode = "";

    private TextView questionNumber;
    private TextView questionText;
    private TextView questionTextDebug;
    private RadioGroup radioGroupSurvey;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionItem = (QuestionItem) getArguments().getSerializable(KEY_QUESTION);
            mPickupCount = getArguments().getInt(KEY_NUMBER);
            mLowerFactorLabel = getArguments().getString(KEY_LOWER_FACTOR_LABEL);
            mDebugMode = getArguments().getString(KEY_DEBUG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        questionText = view.findViewById(R.id.textQuestion);
        questionNumber = view.findViewById(R.id.textNumber);
        radioGroupSurvey = view.findViewById(R.id.radioGroupSurvey);
        questionTextDebug = view.findViewById(R.id.textQuestionDebug);

        questionNumber.setText("質問" + (mPickupCount+1));
        questionText.setText(mQuestionItem.getQuestionSentence());

        if(mDebugMode.equals("有効")){
            radioGroupSurvey.check(R.id.radioButtonVery);
            questionTextDebug.setText(mLowerFactorLabel + "\nUnique : " + mQuestionItem.getQuestionNumber());
        }
        // チェックされたことを感知
        radioGroupSurvey.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // チェック状態変更時に呼び出されるメソッド
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // チェック状態時の処理を記述
                mListener.onFragmentInteraction(mPickupCount);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // ユーザーに見せる質問番号, ユーザーに見せる質問文, 質問が持つ固有番号
    public static SurveyFragment newInstance(int questionNumber, QuestionItem questionItem, String lowerFactorLabel, String debugMode) {
        // フラグメントに渡したいデータをセット
        SurveyFragment fragment = new SurveyFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_QUESTION, questionItem);
        args.putInt(KEY_NUMBER, questionNumber);
        args.putString(KEY_LOWER_FACTOR_LABEL, lowerFactorLabel);
        args.putString(KEY_DEBUG, debugMode);
        fragment.setArguments(args);
        return fragment;
    }

    public String getCheckedRadioButton(){
        int checkedSurvey = radioGroupSurvey.getCheckedRadioButtonId();
        if(checkedSurvey == -1) {
            return "未選択";
        }else{
            RadioButton selectedGender = this.view.findViewById(checkedSurvey);
            return selectedGender.getText().toString(); // 選択された性別のラベル文字列を返す
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int questionNumber);
    }
}
