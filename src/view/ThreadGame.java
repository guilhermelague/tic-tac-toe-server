package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Guilherme Lague
 */
public class ThreadGame extends Thread{
    private String[][] matriz = new String[3][3];
    private final int WINNER_X = 10;
    private final int WINNER_O = 11;
    private final int A_TIE = 12;
    TelaServidor tela_servidor;
    
    public ThreadGame(TelaServidor tela_servidor) {
        this.tela_servidor =  tela_servidor;
    }
    
    @Override
    public void run(){
        startGame();
    }
    
    /**
     * Checa as linahs da matriz 
     * @param client Vetor de socket's conectados
     * @return 1- se encontrar 3 acertos, 0- se não encontrar
     * @throws IOException 
     */
    public int checaLinhas(Socket[] client) throws IOException{
        for(int i = 0; i < 3; i++){
            if(matriz[i][0].equals("x") && matriz[i][1].equals("x") && matriz[i][2].equals("x")){
                System.out.println("JOGADOR 'X' VENCEU!");
                DataOutputStream out1 = new DataOutputStream(client[0].getOutputStream());
                DataOutputStream out2 = new DataOutputStream(client[1].getOutputStream());
                out1.writeUTF(String.valueOf(WINNER_X));
                out2.writeUTF(String.valueOf(WINNER_X));
                return 1;
            }
            if(matriz[i][0].equals("o") && matriz[i][1].equals("o") && matriz[i][2].equals("o")){
                System.out.println("JOGADOR 'O' VENCEU!");
                DataOutputStream out1 = new DataOutputStream(client[0].getOutputStream());
                DataOutputStream out2 = new DataOutputStream(client[1].getOutputStream());
                out1.writeUTF(String.valueOf(WINNER_O));
                out2.writeUTF(String.valueOf(WINNER_O));
                return 1;
            }
        }
        return 0;
    }
    
    /**
     * Checa as colunas da matriz 
     * @param client Vetor de socket's conectados
     * @return 1- se encontrar 3 acertos, 0- se não encontrar
     * @throws IOException 
     */
    public int checaColunas(Socket[] client) throws IOException{
        for(int j = 0; j < 3; j++){
            if(matriz[0][j].equals("x") && matriz[1][j].equals("x") && matriz[2][j].equals("x")){
                System.out.println("JOGADOR 'X' VENCEU!");
                DataOutputStream out1 = new DataOutputStream(client[0].getOutputStream());
                DataOutputStream out2 = new DataOutputStream(client[1].getOutputStream());
                out1.writeUTF(String.valueOf(WINNER_X));
                out2.writeUTF(String.valueOf(WINNER_X));
                return 1;
            }
            if(matriz[0][j].equals("o") && matriz[1][j].equals("o") && matriz[2][j].equals("o")){
                System.out.println("JOGADOR 'O' VENCEU!");
                DataOutputStream out1 = new DataOutputStream(client[0].getOutputStream());
                DataOutputStream out2 = new DataOutputStream(client[1].getOutputStream());
                out1.writeUTF(String.valueOf(WINNER_O));
                out2.writeUTF(String.valueOf(WINNER_O));
                return 1;
            }
        }
        return 0;
    }
    
    /**
     * Checa a diagonal primaria da matriz 
     * @param client Vetor de socket's conectados
     * @return 1- se encontrar 3 acertos, 0- se não encontrar
     * @throws IOException 
     */
    public int checaDiagonalPrimaria(Socket[] client) throws IOException{
        if(matriz[0][0].equals("x") && matriz[1][1].equals("x") && matriz[2][2].equals("x")){
            System.out.println("JOGADOR 'X' VENCEU!");
            DataOutputStream out1 = new DataOutputStream(client[0].getOutputStream());
            DataOutputStream out2 = new DataOutputStream(client[1].getOutputStream());
            out1.writeUTF(String.valueOf(WINNER_X));
            out2.writeUTF(String.valueOf(WINNER_X));
            return 1;
        }
        if(matriz[0][0].equals("o") && matriz[1][1].equals("o") && matriz[2][2].equals("o")){
            System.out.println("JOGADOR 'O' VENCEU!");
            DataOutputStream out1 = new DataOutputStream(client[0].getOutputStream());
            DataOutputStream out2 = new DataOutputStream(client[1].getOutputStream());
            out1.writeUTF(String.valueOf(WINNER_O));
            out2.writeUTF(String.valueOf(WINNER_O));
            return 1;
        }
        return 0;
    }
    
    /**
     * Checa a diagonal secundaria da matriz 
     * @param client Vetor de socket's conectados
     * @return 1- se encontrar 3 acertos, 0- se não encontrar
     * @throws IOException 
     */
    public int checaDiagonalSecundaria(Socket[] client) throws IOException{
        if(matriz[0][2].equals("x") && matriz[1][1].equals("x") && matriz[2][0].equals("x")){
            System.out.println("JOGADOR 'X' VENCEU!");
            DataOutputStream out1 = new DataOutputStream(client[0].getOutputStream());
            DataOutputStream out2 = new DataOutputStream(client[1].getOutputStream());
            out1.writeUTF(String.valueOf(WINNER_X));
            out2.writeUTF(String.valueOf(WINNER_X));
            return 1;
        }
        if(matriz[0][2].equals("o") && matriz[1][1].equals("o") && matriz[2][0].equals("o")){
            System.out.println("JOGADOR 'O' VENCEU!");
            DataOutputStream out1 = new DataOutputStream(client[0].getOutputStream());
            DataOutputStream out2 = new DataOutputStream(client[1].getOutputStream());
            out1.writeUTF(String.valueOf(WINNER_O));
            out2.writeUTF(String.valueOf(WINNER_O));
            return 1;
        }
        return 0;
    }
    
    /**
     * Desenha a matriz dos jogadores
     */
    public void drawnGame(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(matriz[i][j]+" |");
            }
            System.out.println("");
        } 
    }
    
    /**
     * Limpa a matriz inserindo espaços
     */
    public void emptyMat(){
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                matriz[x][y] = " ";
            }
        }
    }
    
    public void startGame() {
        String[][] player = new String[2][20];
        String[] op = new String[2];
        Socket[] client = new Socket[2];
        String op2;
        ServerSocket server_sock = null;
        
        op[0] = "x";
        op[1] = "o";
        
        int i = 0, t = 0, turn = 0, line = 0, col =0;
        
        try {
            server_sock = new ServerSocket(45000);          
            System.out.println("Servidor iniciado, esperando jogadores...");
            tela_servidor.setStatus(server_sock.getLocalPort(), InetAddress.getLocalHost().getHostAddress());
            
            for (i = 0; i < 2; i++){ // apenas 2 clientes
                client[i] = server_sock.accept();
                
                DataInputStream in = new DataInputStream(client[i].getInputStream());
                
                player[i][0] = in.readUTF();
                
                System.out.println("cliente[" + player[i][0] + "], conectou-se");
            }
            
            //depois dos dois se conectarem, deve ser atualizado os nomes  em cada tela
            for(i = 0; i < 2; i++){
                DataOutputStream out = new DataOutputStream(client[i].getOutputStream());
                out.writeUTF(player[0][0]);
                out.writeUTF(player[1][0]);
            }
            System.out.println("devolvi os nomes");
            
            //manda pra cada jogador o simbolo da jogada
            DataOutputStream out_xx = new DataOutputStream(client[0].getOutputStream());
            out_xx.writeUTF(op[0]);
            DataOutputStream out_oo = new DataOutputStream(client[1].getOutputStream());
            out_oo.writeUTF(op[1]);

            //preenche a matriz com espaços vazios
            emptyMat();
            System.out.println("preenchi a matriz vazia");
            System.out.println("jogo iniciado...");
            
            // começa o jogo
            for(t = 0; t <= 10; t++){
                if(t >= 9){
                    System.out.println("turn game = "+ t +", EMPATE!");
                    // avisa o "x" que deu empate
                    DataOutputStream out_x = new DataOutputStream(client[0].getOutputStream());// jogador x
                    out_x.writeUTF(String.valueOf(A_TIE)); 

                    // avisa o "o" que deu empate
                    DataOutputStream out_o = new DataOutputStream(client[1].getOutputStream());// jogador o
                    out_o.writeUTF(String.valueOf(A_TIE));
                    
                    t = 0;
                    emptyMat();
                    System.out.println("reiniciando o game...");
                }
                else{
                    System.out.println("turn game = "+ t);
                
                    if(turn == 0){ //  jogador X
                        System.out.println("Jogador X joga agora, turn = "+turn);
                        // avisa que o turn é dele
                        DataOutputStream out_x = new DataOutputStream(client[0].getOutputStream());// jogador x
                        out_x.writeUTF(String.valueOf(1)); // 1- vez do "x"

                        // avisa o "o" que é pra esperar
                        DataOutputStream out_o = new DataOutputStream(client[1].getOutputStream());// jogador o
                        out_o.writeUTF(String.valueOf(0)); // 0 - "o" espera

                        // recebe do "x", vem pelo meto setplay na clase ClientSocketTCP
                        DataInputStream in = new DataInputStream(client[0].getInputStream());
                        line = Integer.parseInt(in.readUTF());
                        col = Integer.parseInt(in.readUTF());
                        op2 = in.readUTF();

                        //manda pro "o", vai pra classe threadReceive do cliente
                        out_o.writeUTF(String.valueOf(line));
                        out_o.writeUTF(String.valueOf(col));
                        out_o.writeUTF(op2);

                        System.out.println(""+player[turn][0]+": ["+line+"]["+col+"], ["+op[0]+"]");

                        matriz[line][col] = op[0];
                        drawnGame();

                        if(checaLinhas(client) == 1 || checaColunas(client) == 1 || checaDiagonalPrimaria(client) == 1 || checaDiagonalSecundaria(client) == 1){
                            t = 0;
                            emptyMat();
                            System.out.println("reiniciando o game...");
                        }
                        else{
                            turn = 1;
                        }
                    }
                    else{ // jogador O
                        System.out.println("Jogador O joga agora, turn = "+turn);

                        // avisa o x que é pra esperar                    
                        DataOutputStream out_x = new DataOutputStream(client[0].getOutputStream());// jogador x
                        out_x.writeUTF(String.valueOf(0)); // 0 - x espera

                        // avisa que o turn é do "o"
                        DataOutputStream out_o = new DataOutputStream(client[1].getOutputStream());// jogador o
                        out_o.writeUTF(String.valueOf(1));// 1 - vez o

                        // recebe do "o", vem pelo meto setplay na clase ClientSocketTCP
                        DataInputStream in = new DataInputStream(client[1].getInputStream());
                        line = Integer.parseInt(in.readUTF());
                        col = Integer.parseInt(in.readUTF());
                        op2 = in.readUTF();

                        //manda pro "x", vai pra classe threadReceive do cliente
                        out_x.writeUTF(String.valueOf(line));
                        out_x.writeUTF(String.valueOf(col));
                        out_x.writeUTF(op2);

                        System.out.println(""+player[turn][0]+": ["+line+"]["+col+"], ["+op[1]+"]");

                        matriz[line][col] = op[1];

                        drawnGame();

                        if(checaLinhas(client) == 1 || checaColunas(client) == 1 || checaDiagonalPrimaria(client) == 1 || checaDiagonalSecundaria(client) == 1){
                            t = 0;
                            emptyMat();
                            System.out.println("reiniciando o game...");
                        }
                        else{
                            turn = 0;
                        }
                    }
                }
            }
        }   
        catch(IOException | NumberFormatException e) {
            try {
                System.out.println("Reiniciando o servidor...");
                server_sock.close();
                run();
            } catch (IOException ex) {
                System.err.println("Não foi possivel reiniciar o servidor! erro: "+ex.getMessage());
            }
        }
    }
}
