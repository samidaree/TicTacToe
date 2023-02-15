package com.sb.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.sb.tictactoe.databinding.ActivityBoardBinding;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private ActivityBoardBinding binding;
    private static final int[] idArray = {R.id.top_left_square_image, R.id.top_center_square_image, R.id.top_right_square_image,  R.id.center_left_square_image, R.id.center_square_image, R.id.center_right_square_image,  R.id.bottom_left_square_image, R.id.bottom_center_square_image, R.id.bottom_right_square_image} ;
    private final ImageView[]boxes = new ImageView[idArray.length];

    private double bestScore = Double.NEGATIVE_INFINITY;
    private final List<int[]> combinationList = new ArrayList<>();
    private int[]boxPositions = {0,0,0,0,0,0,0,0,0};
    private int playerTurn = 1 ;
    private int totalSelectedBoxes = 1;

    private HardLevelAI ai = new HardLevelAI(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String mode = getIntent().getStringExtra("clickedButton");
        System.out.println("mode : " + mode);

        combinationList.add(new int[] {0,1,2});
        combinationList.add(new int[] {3,4,5});
        combinationList.add(new int[] {6,7,8});
        combinationList.add(new int[] {0,3,6});
        combinationList.add(new int[] {1,4,7});
        combinationList.add(new int[] {2,5,8});
        combinationList.add(new int[] {2,4,6});
        combinationList.add(new int[] {0,4,8});


        for (int i = 0; i<idArray.length;i++){
            boxes[i] = findViewById(idArray[i]);
            int finalI = i;
            boxes[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if (isBoxSelectable(finalI)){
                        if (mode.equalsIgnoreCase("Computer")){
                            if (playerTurn==1){
                                performAction((ImageView) view, finalI);
                            }
                            if (totalSelectedBoxes<9 && !checkResults())
                                bestMove();
                        }

                        else {
                            performAction((ImageView) view, finalI);
                            }
                    }
                }
            });
        }
    }

    private void performAction (ImageView imageView, int selectedBoxPosition){
        boxPositions[selectedBoxPosition] = playerTurn ;
        if (playerTurn ==1){
            imageView.setImageResource(R.drawable.cross);
            if (checkResults()){
                ResultDialog resultDialog = new ResultDialog(BoardActivity.this , binding.player1Name.getText().toString() + " is a Winner ! ", BoardActivity.this);
                resultDialog.setCancelable(false) ;
                resultDialog.show();
            }
            else if (totalSelectedBoxes == 9){
                ResultDialog resultDialog = new ResultDialog(BoardActivity.this, "Match Draw", BoardActivity.this);
                resultDialog.setCancelable(false) ;
                resultDialog.show();
            }
            else{
                changePlayerTurn(2);
                totalSelectedBoxes++;
            }
        }
        else {
            imageView.setImageResource(R.drawable.circle);
            if (checkResults()){
                ResultDialog resultDialog = new ResultDialog(BoardActivity.this , binding.player2Name.getText().toString() + " is a Winner ! ", BoardActivity.this);
                resultDialog.setCancelable(false) ;
                resultDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                resultDialog.show();
            }
            else if (totalSelectedBoxes == 9){
                ResultDialog resultDialog = new ResultDialog(BoardActivity.this, "Match Draw", BoardActivity.this);
                resultDialog.setCancelable(false) ;
                resultDialog.show();
            }
            else{
                changePlayerTurn(1);
                totalSelectedBoxes++ ;
            }
        }
    }

    private void bestMove(){
        int bestSpot = ai.move();

        boxPositions[bestSpot] = 2 ;
        boxes[bestSpot].setImageResource(R.drawable.circle);
        if (checkResults()){
            ResultDialog resultDialog = new ResultDialog(BoardActivity.this , binding.player2Name.getText().toString() + " is a Winner ! ", BoardActivity.this);
            resultDialog.setCancelable(false) ;
            resultDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            resultDialog.show();
        }
        else if (totalSelectedBoxes == 9){
            ResultDialog resultDialog = new ResultDialog(BoardActivity.this, "Match Draw", BoardActivity.this);
            resultDialog.setCancelable(false) ;
            resultDialog.show();
        }
        else {
            changePlayerTurn(1)  ;
            totalSelectedBoxes++ ;

        }
    }

    protected void changePlayerTurn(int currentPlayerTurn){
        playerTurn = currentPlayerTurn ;
        if (playerTurn == 1){
            binding.player1Layout.setBackgroundResource(R.drawable.rounded_square_green_border);
            binding.player2Layout.setBackgroundResource(R.drawable.rounded_square_white_border);
        }
        else {
            binding.player2Layout.setBackgroundResource(R.drawable.rounded_square_green_border);
            binding.player1Layout.setBackgroundResource(R.drawable.rounded_square_white_border);
        }
    }

    protected boolean checkResults(){
        boolean response = false;
        for (int i = 0; i<combinationList.size() && !response; i++){
            final int[]combination = combinationList.get(i);

            if(boxPositions[combination[0]]== playerTurn && boxPositions[combination[1]]==playerTurn && boxPositions[combination[2]]==playerTurn){
                response = true ;
            }
        }
        return response ;
    }

    protected boolean isBoxSelectable(int boxPosition){
        return boxPositions[boxPosition] == 0;
    }

    public void restartMatch(){
        boxPositions = new int[] {0,0,0,0,0,0,0,0,0};
        playerTurn = 1 ;
        totalSelectedBoxes = 1;
        for (int i = 0; i<idArray.length; i++){
            boxes[i].setImageDrawable(null);
        }
        binding.player2Layout.setBackgroundResource(R.drawable.rounded_square_white_border);
        binding.player1Layout.setBackgroundResource(R.drawable.rounded_square_green_border);

    }

    protected int getPlayerTurn(){
        return playerTurn;
    }

    protected void setBoxPositions(int i, int value){
        this.boxPositions[i] = value;
    }

    protected int getTotalSelectedBoxes(){
        return this.totalSelectedBoxes ;
    }

    protected int[]getBoxPositions(){
        return this.boxPositions;
    }


}