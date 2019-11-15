package edu.koidulag.kmk.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.koidulag.kmk.R;
import edu.koidulag.kmk.UserData;

public class HomepageActivity extends FragmentActivity {

    @BindView(R.id.main_name) TextView a1;
    @BindView(R.id.main_class) TextView a2;
    @BindView(R.id.main_team) TextView a3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        a1.setText(UserData.i().getFirstname() + " " + UserData.i().getLastname());
        a2.setText(UserData.i().get_class());

        String s1 = "Teil pole veel tiimi!";
        if (UserData.i().getTeamData() != null) {
            s1 = UserData.i().getTeamData().getTeamName() + " - " + UserData.i().getRole().getTitle();
        }
        a3.setText(s1);
    }

}
