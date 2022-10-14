// Generated by view binder compiler. Do not edit!
package com.example.indulge.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.indulge.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LibrarySearchPlaceBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final FragmentContainerView autocompleteFragment;

  private LibrarySearchPlaceBinding(@NonNull CardView rootView,
      @NonNull FragmentContainerView autocompleteFragment) {
    this.rootView = rootView;
    this.autocompleteFragment = autocompleteFragment;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static LibrarySearchPlaceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LibrarySearchPlaceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.library_search_place, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LibrarySearchPlaceBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.autocomplete_fragment;
      FragmentContainerView autocompleteFragment = ViewBindings.findChildViewById(rootView, id);
      if (autocompleteFragment == null) {
        break missingId;
      }

      return new LibrarySearchPlaceBinding((CardView) rootView, autocompleteFragment);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
