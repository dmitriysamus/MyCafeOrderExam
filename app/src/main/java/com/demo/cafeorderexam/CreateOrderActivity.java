package com.demo.cafeorderexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxLemon;
    private Spinner spinnerChooseCoffee;
    private Spinner spinnerChooseTea;

    private String name;
    private String password;
    private String drink;
    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        Intent intent = getIntent();
        if (intent.hasExtra("name") && intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        } else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }
        textViewHello = findViewById(R.id.textViewHello);
        String hello = String.format(getString(R.string.hello_user), name);
        textViewHello.setText(hello);

        textViewAdditions = findViewById(R.id.textViewAdditions);
        drink = getString(R.string.tea);
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);

        checkBoxSugar = findViewById(R.id.checkBoxSugar);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxLemon = findViewById(R.id.checkBoxLemon);
        spinnerChooseCoffee = findViewById(R.id.spinnerChooseCoffee);
        spinnerChooseTea = findViewById(R.id.spinnerChooseTea);
        stringBuilder = new StringBuilder();


    }

    public void onClickChooseDrink(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonTea) {
            drink = getString(R.string.tea);
            spinnerChooseTea.setVisibility(View.VISIBLE);
            spinnerChooseCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        } else if (id == R.id.radioButtonCoffee){
            drink = getString(R.string.coffee);
            spinnerChooseTea.setVisibility(View.INVISIBLE);
            spinnerChooseCoffee.setVisibility(View.VISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);

    }

    public void onClickSendOrder(View view) {
        stringBuilder.setLength(0);
        if (checkBoxSugar.isChecked()) {
            stringBuilder.append(getString(R.string.sugar)).append(" ");
        }
        if (checkBoxMilk.isChecked()) {
            stringBuilder.append(getString(R.string.milk)).append(" ");
        }
        if (checkBoxLemon.isChecked() && drink.equals(getString(R.string.tea))) {
            stringBuilder.append(getString(R.string.lemon)).append(" ");
        }

        String optionOfDrink = "";
        if (drink.equals(getString(R.string.tea))) {
            optionOfDrink = spinnerChooseTea.getSelectedItem().toString();
        } else if (drink.equals(getString(R.string.coffee))) {
            optionOfDrink = spinnerChooseCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order), name, password, drink, optionOfDrink);

        String additions;
        if (stringBuilder.length() > 0) {
            additions = getString(R.string.need_additions) + stringBuilder.toString();
        } else {
            additions = "";
        }

        String orderFinal = order + additions;
        Intent intent = new Intent (this, OrderDetailActivity.class);
        intent.putExtra("order", orderFinal);
        startActivity(intent);
    }
}