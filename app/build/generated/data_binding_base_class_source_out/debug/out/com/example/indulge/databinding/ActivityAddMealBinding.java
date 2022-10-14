// Generated by view binder compiler. Do not edit!
package com.example.indulge.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.indulge.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAddMealBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnOk;

  @NonNull
  public final EditText etDishComment;

  @NonNull
  public final EditText etDishName;

  @NonNull
  public final EditText etRestaurantName;

  @NonNull
  public final RatingBar rbMealRating;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView3;

  private ActivityAddMealBinding(@NonNull LinearLayout rootView, @NonNull Button btnOk,
      @NonNull EditText etDishComment, @NonNull EditText etDishName,
      @NonNull EditText etRestaurantName, @NonNull RatingBar rbMealRating,
      @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3) {
    this.rootView = rootView;
    this.btnOk = btnOk;
    this.etDishComment = etDishComment;
    this.etDishName = etDishName;
    this.etRestaurantName = etRestaurantName;
    this.rbMealRating = rbMealRating;
    this.textView = textView;
    this.textView2 = textView2;
    this.textView3 = textView3;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAddMealBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAddMealBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_add_meal, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAddMealBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnOk;
      Button btnOk = ViewBindings.findChildViewById(rootView, id);
      if (btnOk == null) {
        break missingId;
      }

      id = R.id.etDishComment;
      EditText etDishComment = ViewBindings.findChildViewById(rootView, id);
      if (etDishComment == null) {
        break missingId;
      }

      id = R.id.etDishName;
      EditText etDishName = ViewBindings.findChildViewById(rootView, id);
      if (etDishName == null) {
        break missingId;
      }

      id = R.id.etRestaurantName;
      EditText etRestaurantName = ViewBindings.findChildViewById(rootView, id);
      if (etRestaurantName == null) {
        break missingId;
      }

      id = R.id.rbMealRating;
      RatingBar rbMealRating = ViewBindings.findChildViewById(rootView, id);
      if (rbMealRating == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      return new ActivityAddMealBinding((LinearLayout) rootView, btnOk, etDishComment, etDishName,
          etRestaurantName, rbMealRating, textView, textView2, textView3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
