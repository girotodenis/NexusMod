package br.com.dsg.appteste.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.dsg.swing.util.Constantes;

public class HomeView extends javax.swing.JPanel {

   /**
    * 
    */
   private static final long serialVersionUID = -5615207212602152683L;

   private JButton botao;
   private JButton botao2;
   private JButton botao3;
   private Brick target = new Brick();

   public class Brick extends JPanel {

      Brick() {
         //setBackground(Color.YELLOW);
         setBorder(BorderFactory.createLineBorder(Color.GREEN));
      }
   }

   public HomeView() {
      //setBackground(Constantes.COR_FUNDO_APP_BAR);
      setLayout(new GridBagLayout());

      GridBagConstraints gbc01 = new GridBagConstraints();
      gbc01.anchor = GridBagConstraints.NORTHWEST;
      gbc01.gridx = GridBagConstraints.RELATIVE;
      gbc01.gridy = GridBagConstraints.RELATIVE;
      gbc01.fill = GridBagConstraints.BOTH;
      gbc01.weightx = 1.0;
      gbc01.weighty = 1.0;

      botao = new JButton();
      botao.setSize(10, 5);
      botao.setName("botao01");
      botao.setLabel("Acao");

      Brick brickBotao = new Brick();
      brickBotao.setBackground(Color.pink);
      brickBotao.setLayout(new GridBagLayout());
      GridBagConstraints gbc02 = new GridBagConstraints();
      gbc02.anchor = GridBagConstraints.NORTHWEST;
      gbc02.gridx = GridBagConstraints.RELATIVE;
      gbc02.gridy = GridBagConstraints.RELATIVE;
      brickBotao.add(botao, gbc02);

      botao2 = new JButton();
      botao2.setSize(10, 5);
      botao2.setName("botao02");
      botao2.setLabel("Só Ir Config");

      Brick brickBotao2 = new Brick();
      brickBotao2.setBackground(Color.pink);
      brickBotao2.setLayout(new GridBagLayout());
      GridBagConstraints gbc03 = new GridBagConstraints();
      gbc03.anchor = GridBagConstraints.NORTHWEST;
      gbc03.gridx = GridBagConstraints.RELATIVE;
      gbc03.gridy = GridBagConstraints.RELATIVE;
      brickBotao2.add(botao2, gbc03);
      
      botao3 = new JButton();
      botao3.setSize(10, 5);
      botao3.setName("botao03");
      botao3.setLabel("Ir/Voltar Config");
      
      Brick brickBotao3 = new Brick();
      brickBotao3.setBackground(Color.pink);
      brickBotao3.setLayout(new GridBagLayout());
      GridBagConstraints gbc04 = new GridBagConstraints();
      gbc04.anchor = GridBagConstraints.NORTHWEST;
      gbc04.gridx = GridBagConstraints.RELATIVE;
      gbc04.gridy = GridBagConstraints.RELATIVE;
      brickBotao3.add(botao3, gbc04);


      target.setBackground(Color.gray);
      target.setLayout(new FlowLayout());
      JLabel jLabel = new JLabel("0");
      jLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
      jLabel.setForeground(new java.awt.Color(0, 0, 0));
      target.add(jLabel);

      add(brickBotao, gbc01);
      add(new Brick(), gbc01);
      add(target, gbc01);
      add(new Brick(), gbc01);
      add(new Brick(), gbc01);

      gbc01.gridy = 1;

      add(brickBotao2, gbc01);
      add(new Brick(), gbc01);
      add(new Brick(), gbc01);
      add(new Brick(), gbc01);
      add(new Brick(), gbc01);

      gbc01.gridy = 2;

      add(brickBotao3, gbc01);
      add(new Brick(), gbc01);
      add(new Brick(), gbc01);
      add(new Brick(), gbc01);
      add(new Brick(), gbc01);
   }

   public JButton getBotao() {
      return botao;
   }

   public JButton getBotao2() {
      return botao2;
   }
   
   public JButton getBotao3() {
	   return botao3;
   }

   public Brick getTarget() {
      return target;
   }

}
