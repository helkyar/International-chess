/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinechess.controller;

import onlinechess.controller.pieces.King;
import onlinechess.controller.pieces.Rook;
import onlinechess.controller.pieces.Queen;
import onlinechess.controller.pieces.Pawn;
import onlinechess.controller.pieces.Bishop;
import onlinechess.controller.pieces.Horse;
 
import onlinechess.views.Board;
import onlinechess.helpers.Conf;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import onlinechess.controller.pieces.PiecesChess;
import onlinechess.views.ChessApp;
import static onlinechess.views.ChessApp.chessico;

/**
 *
 * @author Javier Palacios Botejara
 */
public class Game extends JPanel implements ActionListener{
    private JButton prev;
    private boolean selected;
    private String piece;
    private Icon icon;
    private Color bg;
    
    public Conf conf;
    public Board board;
    private boolean blackTurn;
    
    public String pawnPessant; //pawn reference (*)
    public boolean enPessant; //en pessant possibility
    public int posPessant; //position of phantom pawn
    private int tgPessant; //position of phantom pawn Parent(*)
    private int reset; //counter(*)
    
    private Map<Integer, String> king;
    public ArrayList<Integer> checkPos;
    public Map<Integer, Integer> castlingAllowed;
    private Map<Integer, Integer> castlingDef;
    private boolean initializing = true;
    
    public Game(){
        boolean isWhite = true;
        int choose = JOptionPane.showConfirmDialog(this, "Quieres ser blancas?", "Elige", 0, 1, chessico);
        if(choose == 0) {isWhite = true;} //yes
        else if(choose == 1) {isWhite = false;}//no
        
        Conf conf= new Conf();
        
        board = new Board(conf.size(), conf.init(), isWhite);
        board.startBoard(this);
        
        JScrollPane border = new JScrollPane(board);
        border.setPreferredSize(conf.context);
        add(border);
        
        castlingAllowed = new HashMap<>();
        castlingDef = new HashMap<>();
        isCheck();
    //test ------------------------------------
        JButton btn = new JButton("T");
        btn.addActionListener(this);  
        add(btn);
    //----------------------------------------
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String tile = btn.getName();
        //Test -----------------------------------------------------------------
//        System.out.println(board.getPosition(btn));      
//        System.out.println(board.getTilePiece(board.getPosition(btn)));
        if(e.getActionCommand().equals("T")){test();return;}
        
        //Turn checker ---------------------------------------------------------
        boolean correctTurn = (
            (Conf.WHITES.contains(tile) && !blackTurn)||
            (Conf.BLACKS.contains(tile) && blackTurn)
        );
        
        //Select tile ----------------------------------------------------------
        if(!selected && !tile.equals("-") && correctTurn){ 
            prev = btn;
            piece = tile;
            icon = btn.getIcon();
            prev.setBackground(Conf.slct);  
                      
            selected = true;
            board.paint(prev, board.w, board.h, board, conf, checkPos, castlingAllowed, pawnPessant, enPessant, posPessant);  
            blackTurn = !blackTurn;
            
        //Move piece if allowed ------------------------------------------------
        } else if(selected && allowedMove(btn, tile)){  
            if(enPessant && reset==0){enPessant = false;}
            reset = 0;
            prev.setIcon(null);
            prev.setName("-");
            btn.setName(piece);
            btn.setIcon(icon);
            
            selected = false;
            board.storeState(); 
            board.unPaint();
            
            isThereaWinner();
            isPawnInTheEnd(btn);
            
        //deselect without move ------------------------------------------------   
        } else if (selected) {
            selected = false;
            board.unPaint();
            blackTurn = !blackTurn;
        }
        
        isCheck();
    }    
    
    /**
     * Returns true if the move is allowed in the game 
     * @param btn source button clicked
     * @param target tile to move to.
     * @return 
     */
    private boolean allowedMove(JButton btn, String target){
        int to = board.getPosition(btn);
        int from = board.getPosition(prev);
     
        //Pieces allowed moves          
        if(piece.equalsIgnoreCase("P")){return pawnWithEnpessant(from, to, piece, target);}    
        else if(piece.equalsIgnoreCase("R")){return Rook.allowed(from, to, piece, target, board.w, board.h, board);}            
        else if(piece.equalsIgnoreCase("H")){return Horse.allowed(from, to, piece, target, board.w, board.h);}            
        else if(piece.equalsIgnoreCase("B")){return Bishop.allowed(from, to, piece, target, board.w, board.h, board);}            
        else if(piece.equalsIgnoreCase("Q")){return Queen.allowed(from, to, piece, target, board.w, board.h, board);}            
        else if(piece.equalsIgnoreCase("K")){return kingWithCastling(from, to, target);}
        
        return false;
    }        

    private boolean kingWithCastling(int from, int to, String target) {
        boolean allowed = King.allowed(from, to, piece, target, board.w, board.h, board, checkPos, castlingAllowed);
        
        if(from-to == -2 && allowed){ //down               
                board.setTilePiece((from+to)/2, board.getTilePiece(to+1));
                board.setTilePiece(to+1, "-");
                
        } else if(from-to == 2 && allowed){ //up
                board.setTilePiece((from+to)/2, board.getTilePiece(to-1));
                board.setTilePiece(to-1, "-");
        }
        
        return allowed;    
    }
    
    private boolean pawnWithEnpessant(int from, int to, String pawn, String target) {
        if(Math.abs(from-to)==16){
            enPessant=true; posPessant=(from+to)/2; pawnPessant=pawn; tgPessant=to; 
            reset=1;
            
        } else if(enPessant && to == posPessant && !piece.equals(pawnPessant)){
            board.setTilePiece(tgPessant, "-");
            return true;
        }
        
        return Pawn.allowed(from, to, piece, target, board.w, board.h, board, conf);
    }

    private void isPawnInTheEnd(JButton btn){
        boolean firstRow = board.getPosition(btn) <= board.w;
        boolean finalRow = board.getPosition(btn) > board.w*board.h-board.w;
        boolean isPawn = piece.equalsIgnoreCase("P");
        if((firstRow || finalRow) && isPawn){  
           JOptionPane.showOptionDialog(this, new Promotion(piece), "Select a piece", 1, 1, chessico, new Object[]{},null);
           String select = Promotion.getSelectedPiece();
           btn.setName(select);
           btn.setIcon(new ImageIcon(Conf.getImg(select)));
        }
    }
    
    public void isCheck(){ 
        checkPos = new ArrayList<>();
        boolean check = false;   
        //Get a Record of all kings and positions
        king = new HashMap<>();
        for(int i = 1; i <= board.w*board.h; i++){            
            String tile = board.getTilePiece(i);            
            if(tile.equalsIgnoreCase("K")){king.put(i,tile);}
        }
        
        //Foer every king check if is in danger
        for(int pos : king.keySet()){            
            for(int i = 1; i <= board.w*board.h; i++){
                String tile = board.getTilePiece(i);
                
                if(!board.isTileEmpty(i) && Conf.WHITES.contains(tile) && king.get(pos).equals("K")) {         
                    if(tile.equalsIgnoreCase("R"))     {check = Rook.allowed(i, pos, tile, king.get(pos), board.w, board.h, board) ? true : check;}            
                    else if(tile.equalsIgnoreCase("H")){check = Horse.allowed(i, pos, tile, king.get(pos), board.w, board.h) ? true : check;}            
                    else if(tile.equalsIgnoreCase("B")){check = Bishop.allowed(i, pos, tile, king.get(pos), board.w, board.h, board) ? true : check;}            
                    else if(tile.equalsIgnoreCase("Q")){check = Queen.allowed(i, pos, tile, king.get(pos), board.w, board.h, board) ? true : check;}            
                    else if(tile.equalsIgnoreCase("K")){check = King.allowed(i, pos, tile, king.get(pos), board.w, board.h, board, checkPos, castlingAllowed) ? true : check;}            
                    else if(tile.equalsIgnoreCase("P")){check = Pawn.allowed(i, pos, tile, king.get(pos), board.w, board.h, board, conf) ? true : check;}
                    if(check){board.paintCheck(pos);checkPos.add(pos);}
                    check = false;
                    
                } else if(!board.isTileEmpty(i) && Conf.BLACKS.contains(tile) && king.get(pos).equals("k")) {        
                    if(tile.equalsIgnoreCase("R"))     {check = Rook.allowed(i, pos, tile, king.get(pos), board.w, board.h, board) ? true : check;}            
                    else if(tile.equalsIgnoreCase("H")){check = Horse.allowed(i, pos, tile, king.get(pos), board.w, board.h) ? true : check;}            
                    else if(tile.equalsIgnoreCase("B")){check = Bishop.allowed(i, pos, tile, king.get(pos), board.w, board.h, board) ? true : check;}            
                    else if(tile.equalsIgnoreCase("Q")){check = Queen.allowed(i, pos, tile, king.get(pos), board.w, board.h, board) ? true : check;}            
                    else if(tile.equalsIgnoreCase("K")){check = King.allowed(i, pos, tile, king.get(pos), board.w, board.h, board, checkPos, castlingAllowed) ? true : check;}            
                    else if(tile.equalsIgnoreCase("P")){check = Pawn.allowed(i, pos, tile, king.get(pos), board.w, board.h, board, conf) ? true : check;} 
                    if(check){board.paintCheck(pos);checkPos.add(pos);}
                    check = false;
                }                 
                storeCastlingPairs(king.get(pos), pos);
            } 
        }
            if(!initializing){
                castlingAllowed.clear();
                castlingAllowed.putAll(castlingDef);
                castlingDef.clear();
                
            }
            initializing = false;
    }
    

    private void storeCastlingPairs(String k, int pos) {
        //Store castling pairs
        if(initializing){
            if(board.getTilePiece(pos-3).equalsIgnoreCase("R") && !PiecesChess.isDiffTeam(k, board.getTilePiece(pos - 3), board.w, board.h))
            {castlingAllowed.put(pos, pos-3);}
            if(board.getTilePiece(pos+3).equalsIgnoreCase("R") && !PiecesChess.isDiffTeam(k, board.getTilePiece(pos + 3), board.w, board.h))
            {castlingAllowed.put(pos, pos+3);}
        //Remove if moved (not working)
        } else if (castlingAllowed.containsKey(pos) && !board.getTilePiece(castlingAllowed.get(pos)).equals("-") &&
          !PiecesChess.isDiffTeam(board.getTilePiece(pos), board.getTilePiece(castlingAllowed.get(pos)), board.w, board.h)){
            castlingDef.put(pos, castlingAllowed.get(pos));
        }        
    }

    private void isThereaWinner() {
        if(!board.getState().contains("k")){
            JOptionPane.showMessageDialog(this, "End-Game", "Black Wins!", 0, chessico);
            board.setState(board.record.get(0));
            blackTurn = false;
        }else if(!board.getState().contains("K")){
            JOptionPane.showMessageDialog(this, "End-Game", "White Wins!", 0, chessico);
            board.setState(board.record.get(0));
            blackTurn = false;
        }
        
    }

    private void test() {
        String choose = JOptionPane.showInputDialog(board, "Set state");
        board.setState(board.record.get(Integer.parseInt(choose)));
        if(Integer.parseInt(choose) % 2 == 0){blackTurn = false;}
    }
}

/**
 * Pawn bounds
 * Bishop bugs
 * Castling **
 */