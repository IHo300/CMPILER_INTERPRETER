import static org.antlr.v4.runtime.CharStreams.fromString;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.antlr.runtime.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.Lexer;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
//import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {


        JFrame f = new JFrame();
        f.setSize(600,400);

        JTextArea t = new JTextArea();
        f.add(t);

        f.setVisible(true);

        JMenuBar m = new JMenuBar();
        f.setJMenuBar( m );

        JMenu m_file = new JMenu("Load");
        m.add( m_file);

        JMenuItem i_load = new JMenuItem("Load file");
        m_file.add(i_load);

        i_load.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String temps = new String();
                String temps2 = new String();

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


                CodePointCharStream input = fromString(temps);
                CLexer lexer = new CLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                CParser parser = new CParser(tokens);

                parser.removeErrorListeners();
                parser.setErrorHandler(new DefaultErrorStrategy());
                parser.addErrorListener(ErrorListener.INSTANCE);

                //parsetreeWalker
                CParser.CompilationUnitContext tree = parser.compilationUnit();
                ParseTreeWalker walker = new ParseTreeWalker();
                CBaseListener listener = new CBaseListener();
                walker.walk(listener,tree);


                ArrayList<String> fromList = ErrorListener.errorsArray;
                for(int i = 0;i<fromList.size();i++){
                    temps2 = temps2.concat(fromList.get(i)+"\r\n");
                }
                if(ErrorListener.curlyBraceError==true){
                    temps2 = temps2.concat(ErrorListener.later);
                }

                t.setText(temps2);
            }
        });



//        try{
//            File file = new File("D:\\antlrdev\\input.txt");
//            FileReader filereader = new FileReader(file);
//            int singleCh = 0;
//            while((singleCh = filereader.read()) != -1){
//                temps = temps.concat((char)singleCh+"");
//            }
//            filereader.close();
//        }catch (FileNotFoundException e) {
//            // TODO: handle exception
//        }catch(IOException e){
//            System.out.println(e);
//        }


//
//        CodePointCharStream input = fromString(temps);
//        CLexer lexer = new CLexer(input);
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        CParser parser = new CParser(tokens);
//
//        parser.removeErrorListeners();
//        parser.setErrorHandler(new DefaultErrorStrategy());
//        parser.addErrorListener(ErrorListener.INSTANCE);


        //parser.compilationUnit();



        //generate parse tree
//        ParseTree tree = parser.compilationUnit();




        //Mylistener listener = new Mylistener();


        //ParseTreeWalker walker = new ParseTreeWalker();
        //walker.walk(listener, tree);


//        ArrayList<String> fromList = ErrorListener.errorsArray;
//        for(int i = 0;i<fromList.size();i++){
//            System.out.println(fromList.get(i));
//        }
//        if(ErrorListener.curlyBraceError==true){
//            System.out.println(ErrorListener.later);
//        }




        //JavaTreeExtractorVisitor calVisitor = JavaTreeExtractorVisitor();



        //System.out.println(tree.toStringTree(parser));
        

        // try {
	    // 	String a;
	    // 	File file = new File("D:\\antlrdev\\input.txt");
        //     Scanner scanner = new Scanner(file);//scanner currently has the whole content of file 
            

	    // 	while (scanner.hasNextLine()) {
		// 		a=scanner.nextLine();
		// 		CodePointCharStream inputStream = fromString(a);
		// 		Java9Lexer lexer = new Java9Lexer(inputStream);
		// 		Token token = lexer.nextToken();
		// 		while(token.getType() != Token.EOF){
		// 			System.out.println(token.getText() + "  =  " + Java9Lexer.VOCABULARY.getSymbolicName(token.getType()));
		// 			token = lexer.nextToken();
		// 		}
        //     }
        //     scanner.close();
		// } catch (FileNotFoundException e) {
		// 	e.printStackTrace();
		// }

        

        // Lexer lexer = new Java9Lexer(input);



    }
}
