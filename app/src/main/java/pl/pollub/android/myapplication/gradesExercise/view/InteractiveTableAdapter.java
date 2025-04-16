package pl.pollub.android.myapplication.gradesExercise.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.pollub.android.myapplication.gradesExercise.data.GradeModel;
import pl.pollub.android.myapplication.R;

public class InteractiveTableAdapter extends RecyclerView.Adapter<InteractiveTableAdapter.GradeViewHolder> {
    private List<GradeModel> gradeList = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_row, parent, false);
        return new GradeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractiveTableAdapter.GradeViewHolder holder, int position) {
        String[] subjectNames = context.getResources().getStringArray(R.array.subjects);
        holder.radioGroup.setTag(gradeList.get(position));
        holder.radioGroup.check(R.id.RB2+gradeList.get(position).getGrade()-2);

        holder.radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            GradeModel gradeModel = (GradeModel) radioGroup.getTag();
            if (i == R.id.RB2) {
                gradeModel.setGrade(2);
                gradeModel.setDescription("Niedostateczny");
            } else if (i == R.id.RB3) {
                gradeModel.setGrade(3);
                gradeModel.setDescription("Dostateczny");
            } else if (i == R.id.RB4) {
                gradeModel.setGrade(4);
                gradeModel.setDescription("Dobry");
            } else if (i == R.id.RB5) {
                gradeModel.setGrade(5);
                gradeModel.setDescription("Bardzo dobry");
            }
        });
        String textViewString = subjectNames[position%5] + ", ocena " + ((position/5)+1);
        holder.gradeName.setText(textViewString);
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    public void setGradeList(List<GradeModel> gradeList) {
        this.gradeList = gradeList;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        private TextView gradeName;
        private RadioGroup radioGroup;

        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeName = itemView.findViewById(R.id.gradeText);
            radioGroup = itemView.findViewById(R.id.radioGroup);

        }
    }
}
