package com.example.myapplication.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultTableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultTableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "title";
    private static final String T_SCORE = "t_score";

    private TextView title;
    private TextView tScore;

    // TODO: Rename and change types of parameters
    private String mTitle;
    private float mTScore;

    private OnFragmentInteractionListener mListener;

    public ResultTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Factors Title.
     * @param tScore Factors Tscore.
     * @return A new instance of fragment ResultTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultTableFragment newInstance(String title, float tScore) {
        ResultTableFragment fragment = new ResultTableFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putFloat(T_SCORE, tScore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mTScore = getArguments().getFloat(T_SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_table, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.textViewResultTitle);
        tScore = (TextView) view.findViewById(R.id.textViewTScore);
        title.setText(mTitle);
        tScore.setText(""+mTScore);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onFragmentInteraction(Uri uri);
    }
}
