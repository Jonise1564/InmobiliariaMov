import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliaria.databinding.FragmentContratosBinding;
import com.example.inmobiliaria.ui.contratos.ContratosViewModel;

public class ContratosFragment extends Fragment {

    private FragmentContratosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContratosViewModel contratosViewModel =
                new ViewModelProvider(this).get(ContratosViewModel.class);

        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textContratos;
        contratosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
