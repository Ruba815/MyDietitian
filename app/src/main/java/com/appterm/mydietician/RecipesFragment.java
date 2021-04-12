package com.appterm.mydietician;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class RecipesFragment extends Fragment {


    ArrayList<Recipe> recipesAfterLoad = new ArrayList<>();
    ArrayList<Category> categoriesAfterLoad = new ArrayList<>();
    AdapterRecipe adapterRecipe;
    AdapterCategory adapterCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipes, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        adapterRecipe = new AdapterRecipe(getActivity(),recipesAfterLoad);
        recyclerView.setAdapter(adapterRecipe);
        adapterRecipe.setOnItemClickListener(new AdapterRecipe.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                Intent intent = new Intent(getActivity(),DisplayRecipeActivity.class);
                intent.putExtra("recipe",recipe);
                startActivity(intent);
            }
        });


         adapterCategory = new AdapterCategory(getActivity(),categoriesAfterLoad);
        categoryRecyclerView.setAdapter(adapterCategory);

        adapterCategory.setOnItemClickListener(new AdapterCategory.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {

                Toast.makeText(getActivity(), ""+category.getCategory_id(), Toast.LENGTH_SHORT).show();
                AsyncTask<Integer,Void,ArrayList<Recipe>> taskRecipe = new AsyncTask<Integer, Void, ArrayList<Recipe>>() {
                    @Override
                    protected ArrayList<Recipe> doInBackground(Integer... integers) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                        databaseAccess.open();
                        ArrayList<Recipe> recipes = databaseAccess.getRecipes(integers[0]);
                        databaseAccess.close();
                        return recipes;
                    }

                    @Override
                    protected void onPostExecute(ArrayList<Recipe> recipes) {
                        recipesAfterLoad.clear();
                        super.onPostExecute(recipes);
                        recipesAfterLoad.addAll(recipes);
                        adapterRecipe.notifyDataSetChanged();

                    }
                };

                taskRecipe.execute(category.getCategory_id());
            }
        });





        taskCategory.execute();
        taskRecipe.execute(1);


        return view;
    }
    AsyncTask<Integer,Void,ArrayList<Recipe>> taskRecipe = new AsyncTask<Integer, Void, ArrayList<Recipe>>() {
        @Override
        protected ArrayList<Recipe> doInBackground(Integer... integers) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
            databaseAccess.open();
            ArrayList<Recipe> recipes = databaseAccess.getRecipes(integers[0]);
            databaseAccess.close();
            return recipes;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            super.onPostExecute(recipes);
            recipesAfterLoad.addAll(recipes);
            adapterRecipe.notifyDataSetChanged();

        }
    };


    AsyncTask<Void,Void,ArrayList<Category>> taskCategory = new AsyncTask<Void, Void, ArrayList<Category>>() {
        @Override
        protected ArrayList<Category> doInBackground(Void... integers) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
            databaseAccess.open();
            ArrayList<Category> categories = databaseAccess.getCategories();
            databaseAccess.close();
            return categories;
        }

        @Override
        protected void onPostExecute(ArrayList<Category> categories) {
            super.onPostExecute(categories);
            categoriesAfterLoad.addAll(categories);
            adapterCategory.notifyDataSetChanged();

        }
    };
}