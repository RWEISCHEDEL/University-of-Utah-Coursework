package edu.utah.cs4530.gotnothing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuoteService.getInstance().getQuoteSummaries(new QuoteService.QuoteSummaryListener() {
            @Override
            public void quoteSummaryRecieved(Set<String> quoteIndentifiers) {

            }
        });
    }
}
