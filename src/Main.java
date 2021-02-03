import ANTLR.CorgiError;
import Builder.BuildChecker;
import Builder.ParserHandler;
import Execution.ExecutionManager;
import Semantics.LocalScopeHandler;
import Statements.StatementControlOverseer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static TextArea out = new TextArea("",0,60,TextArea.SCROLLBARS_VERTICAL_ONLY);


    public static void gui(){    }

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        BuildChecker.initialize();
        ExecutionManager.initialize();
        StatementControlOverseer.initialize();


        Frame f = new Frame();
        f.setSize(500,820);

        Panel panel1 = new Panel();
        panel1.setSize(500,600);

        Panel panel2 = new Panel();
        panel2.setSize(500,200);



        MenuBar m = new MenuBar();
        f.setMenuBar( m );

        Menu m_menu = new Menu("Menu");
        m.add(m_menu);

        MenuItem i_load = new MenuItem("Load");
        m_menu.add(i_load);

        MenuItem i_run = new MenuItem("Run");
        m_menu.add(i_run);



        TextArea t = new TextArea("",35,60,TextArea.SCROLLBARS_VERTICAL_ONLY);
        t.setSize(500,600);

        panel1.add(t);


        out.setEditable(false);
        out.setSize(500,200);

        panel2.add(out);

        f.add(panel1,"North");
        f.add(panel2,"South");


        f.setVisible(true);

        i_run.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {
//                    controller.run(t.getText(), "");
                    ExecutionManager.reset();
                    LocalScopeHandler.reset();
                    //  SymbolTableManager.reset();
                    BuildChecker.reset();
                    StatementControlOverseer.reset();

                    resetConsole();

                    ParserHandler.getInstance().parseText(t.getText());

                    if(BuildChecker.getInstance().canExecute()) {
                        ExecutionManager.getInstance().executeAllActions();
                        System.out.println("BuildChecker executed");
                        //this.mViewPager.setCurrentItem(1);
                    }
                    else {
                        System.out.println("Fix identified errors before executing!");
                    }



                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                System.out.println("run button");
                out.setText("runrun");
            }
        });


        i_load.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String temps = new String();

                FileDialog dialog = new FileDialog(f, "Load", FileDialog.LOAD);
                dialog.setFile("*.txt;");
                dialog.setVisible(true);

                String path = dialog.getDirectory() + dialog.getFile();

                if( dialog.getFile() == null ) return;



                try{
                    FileReader filereader = new FileReader(path);
                    int singleCh = 0;
                    while((singleCh = filereader.read()) != -1){
                        temps = temps.concat((char)singleCh+"");
                    }
                    filereader.close();
                }catch (FileNotFoundException e2) {
                    // TODO: handle exception
                }catch(IOException e2){
                    System.out.println(e2);
                }


                t.setText(temps);
            }
        });
    }

    public static void appendErrorInConsole(CorgiError e) {

        String errorPrefix = new String(e.getErrorPrefix());

        String line = new String(e.getLineLayout());

        String errorSuffix = new String(e.getErrorSuffix());


            out.setText(out.getText()+errorPrefix);
            out.setText(out.getText()+line);
            out.setText(out.getText()+errorSuffix);
            out.setText(out.getText()+"\n");

    }

    public static void resetConsole() {
        String consoleText = new String("Console: \n");

        out = new TextArea("",0,60,TextArea.SCROLLBARS_VERTICAL_ONLY);
    }

    public static void printInConsole(String text) {
        out.setText(out.getText()+text);
    }
}