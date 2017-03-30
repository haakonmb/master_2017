package mordbad.master.dss;

/**
 * Created by haakon on 30/09/16.
 */
public class Question {


    String question ="";
    String[] option;
    String answer ="N/A";
    int optionNum = -1;
    int[] tags;
//    int answerOption = 0;


    public Question(){

    }

    public Question(String question,  String[] options){
        this.question = question;
        this.option = options;


    }


    public boolean setAnswer(int optionNum){
        this.optionNum = optionNum;
        if(optionNum < option.length+1){
            answer = option[optionNum];
            return true;
        }
        else return false;


    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer(){
        return answer;
    }

    public String[] getOptions(){
        return option;
    }

    public int getOptionNum(){
        return optionNum;
    }

    public DecisionConstraint generateConstraint() {

        //TODO: fix so it actually generates constraints
        return null;
    }
}
