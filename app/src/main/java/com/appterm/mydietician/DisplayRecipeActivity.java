package com.appterm.mydietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        TextView name = findViewById(R.id.name);
        TextView steps = findViewById(R.id.steps);
        TextView ingredients = findViewById(R.id.ingredients);
        ImageView image = findViewById(R.id.image);


        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        String uri = "@drawable/"+recipe.getImage();  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);

        image.setImageDrawable(res);

        name.setText(recipe.getRecipe_name());
        getSupportActionBar().setTitle(recipe.getRecipe_name());
        ingredients.setText(Html.fromHtml(recipe.getIngredients()));
        steps.setText(Html.fromHtml(recipe.getSteps()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}