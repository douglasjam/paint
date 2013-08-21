package com.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author douglasjam
 * @date 09-21-2012 20:30
 */
public final class PaintD extends javax.swing.JFrame {

    Cursor cursorMira, cursorContaGotas, cursorQuadrado,
            cursorPintar, cursorLapis, cursorSelecionado;
    Color corLinha, corFundo;
    String itemSelecionado;
    String rodapeCordenada = "";
    String msgInformacao = "";
    String texto = "";
    int redondura = 50;
    int anguloInicial = 50;
    int anguloArco = 50;
    boolean drawing;
    int xi, yi, xf, yf;
    ArrayList<Point> pontos = null;

    public PaintD() {

        initComponents();

        //----------------------------------------------------------------------
        // Seta icon e titulo do programa
        //----------------------------------------------------------------------

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/com/resources/icones/djampaint.png"));
            this.setTitle("DjamPaint v1.0");
            super.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("Erro ao setar o icone:" + e.getMessage());
        }

        //

        itemSelecionado = "";
        cursorSelecionado = Cursor.getDefaultCursor();
        this.setCursor(cursorSelecionado);
        pontos = new ArrayList<Point>();

        setCOR("LINHA", Color.BLACK);
        setCOR("FUNDO", Color.WHITE);

        //----------------------------------------------------------------------
        // Adiciona cursores
        //----------------------------------------------------------------------

        criaCursores();

        //----------------------------------------------------------------------
        // Adiciona listeners
        //----------------------------------------------------------------------

        adicionaListenerMovedRecursivo(this);
        this.setLocationRelativeTo(null);

        // set o listmodel de camadas
        jlCamadas.setModel(((PainelDesenho) pnlAreaTrabalho).getDefaultListModel());


    }

    public void selecionaItemPaleta(String item) {

        habilitaTodaPaleta();

        if (item.equals("ABRIR")) {
            btnAbrir.setEnabled(false);
        } else if (item.equals("SALVAR")) {
            btnSalvar.setEnabled(false);
        } else if (item.equals("LAPIS")) {
            btnLapis.setEnabled(false);
            itemSelecionado = "LAPIS";
        } else if (item.equals("TEXTO")) {
            btnTexto.setEnabled(false);
            itemSelecionado = "TEXTO";
        } else if (item.equals("LINHA")) {
            btnLinha.setEnabled(false);
            itemSelecionado = "LINHA";
        } else if (item.equals("BORRACHA")) {
            btnBorracha.setEnabled(false);
            itemSelecionado = "BORRACHA";
        } else if (item.equals("QUADRADO")) {
            itemSelecionado = "QUADRADO";
            btnQuadrado.setEnabled(false);
        } else if (item.equals("QUADRADOOPACO")) {
            itemSelecionado = "QUADRADOOPACO";
            btnQuadradoOpaco.setEnabled(false);
        } else if (item.equals("QUADRADOARREDONDADO")) {
            itemSelecionado = "QUADRADOARREDONDADO";
            btnQuadradoArredondado.setEnabled(false);
        } else if (item.equals("QUADRADOARREDONDADOOPACO")) {
            itemSelecionado = "QUADRADOARREDONDADOOPACO";
            btnQuadradoArredondadoOpaco.setEnabled(false);
        } else if (item.equals("TRIANGULO")) {
            itemSelecionado = "TRIANGULO";
            btnTriangulo.setEnabled(false);
        } else if (item.equals("TRIANGULOOPACO")) {
            itemSelecionado = "TRIANGULOOPACO";
            btnTrianguloOpaco.setEnabled(false);
        } else if (item.equals("POLIGONO")) {
            itemSelecionado = "POLIGONO";
            btnPoligono.setEnabled(false);
        } else if (item.equals("POLIGONOOPACO")) {
            itemSelecionado = "POLIGONOOPACO";
            btnPoligonoOpaco.setEnabled(false);
        } else if (item.equals("ARCO")) {
            itemSelecionado = "ARCO";
            btnArco.setEnabled(false);
        } else if (item.equals("CLEAR")) {
            itemSelecionado = "CLEAR";
            btnClear.setEnabled(false);
        } else if (item.equals("CONTAGOTAS")) {
            itemSelecionado = "CONTAGOTAS";
            btnContaGotas.setEnabled(false);
        } else if (item.equals("CIRCULO")) {
            itemSelecionado = "CIRCULO";
            btnCirculo.setEnabled(false);
        } else if (item.equals("CIRCULOOPACO")) {
            itemSelecionado = "CIRCULOOPACO";
            btnCirculoOpaco.setEnabled(false);
        } else if (item.equals("BALDETINTA")) {
            itemSelecionado = "BALDETINTA";
            btnBaldeTinta.setEnabled(false);
        } else if (item.equals("REDIMENSIONAR")) {
            btnRedimensionar.setEnabled(false);
        } else if (item.equals("ROTACIONAR")) {
            btnRotacionar.setEnabled(false);
        } else {
        }
    }

    public void habilitaTodaPaleta() {
        btnAbrir.setEnabled(true);
        btnSalvar.setEnabled(true);
        btnLapis.setEnabled(true);
        btnTexto.setEnabled(true);
        btnLinha.setEnabled(true);
        btnBorracha.setEnabled(true);
        btnQuadrado.setEnabled(true);
        btnQuadradoOpaco.setEnabled(true);
        btnQuadradoArredondado.setEnabled(true);
        btnQuadradoArredondadoOpaco.setEnabled(true);
//        btnContaGotas.setEnabled(true);
        btnTriangulo.setEnabled(true);
        btnTrianguloOpaco.setEnabled(true);
        btnPoligono.setEnabled(true);
        btnPoligonoOpaco.setEnabled(true);
        btnArco.setEnabled(true);
        btnClear.setEnabled(true);
        btnCirculo.setEnabled(true);
        btnCirculoOpaco.setEnabled(true);
//        btnBaldeTinta.setEnabled(true);
//        btnRedimensionar.setEnabled(true);
//        btnRotacionar.setEnabled(true);
    }

    public void atualizaRodapeInfo() {
        if (msgInformacao.equals("")) {
            lblRodapeInfo.setText(rodapeCordenada);
        } else {
            lblRodapeInfo.setText(msgInformacao);
        }
    }

    public void adicionaListenerMovedRecursivo(Container pai) {
        // adiciona um listener de posicao do mouse para mostrar no rodape
        for (Component filho : pai.getComponents()) {
            filho.addMouseMotionListener(new MouseAdapter() {
                public void mouseMoved(MouseEvent evt) {
                    rodapeCordenada = "Mouse [" + evt.getX() + ", " + evt.getY() + "]";
                    atualizaRodapeInfo();
                }
            });
//            adicionaListenerMovedRecursivo((Container) filho);
        }
    }

    public int[] getXPoints() {

        int xPoints[] = new int[pontos.size()];
        for (int i = 0; i < pontos.size(); i++) {
            xPoints[i] = (int) pontos.get(i).getX();
        }

        return xPoints;
    }

    public int[] getYPoints() {
        int yPoints[] = new int[pontos.size()];
        for (int i = 0; i < pontos.size(); i++) {
            yPoints[i] = (int) pontos.get(i).getY();
        }

        return yPoints;
    }

    public void criaCursores() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = new javax.swing.ImageIcon(getClass().getResource("/com/resources/cursores/quadrado.png")).getImage();
        Point hotSpot = new Point(16, 16);

        Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "QUADRADO");
        cursorQuadrado = cursor;

        //

        toolkit = Toolkit.getDefaultToolkit();
        image = new javax.swing.ImageIcon(getClass().getResource("/com/resources/cursores/pencil.png")).getImage();
        hotSpot = new Point(15, 18);

        cursor = toolkit.createCustomCursor(image, hotSpot, "LAPIS");
        cursorLapis = cursor;

        //

        toolkit = Toolkit.getDefaultToolkit();
        image = new javax.swing.ImageIcon(getClass().getResource("/com/resources/cursores/mira.png")).getImage();
        hotSpot = new Point(16, 16);

        cursor = toolkit.createCustomCursor(image, hotSpot, "MIRA");
        cursorMira = cursor;

        //

        toolkit = Toolkit.getDefaultToolkit();
        image = new javax.swing.ImageIcon(getClass().getResource("/com/resources/cursores/contagotas.png")).getImage();
        hotSpot = new Point(16, 16);

        cursor = toolkit.createCustomCursor(image, hotSpot, "PINTAR");
        cursorContaGotas = cursor;

        //

        toolkit = Toolkit.getDefaultToolkit();
        image = new javax.swing.ImageIcon(getClass().getResource("/com/resources/cursores/pintar.png")).getImage();
        hotSpot = new Point(16, 16);

        cursor = toolkit.createCustomCursor(image, hotSpot, "PINTAR");
        cursorPintar = cursor;

    }

    public void carregarArquivo() {

        BufferedImage image;

        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Todos os arquivos de imagem", "bmp", "dib", "jpeg", "jpg", "jfif", "gif", "tiff", "tif", "png", "ico"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                image = ImageIO.read(new File(fileChooser.getSelectedFile().getPath()));
                ((PainelDesenho) pnlAreaTrabalho).adicionaImagem(image, "IMG");
            } catch (IOException ex) {
                Logger.getLogger(PaintD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void salvaArquivo() {

        String filename;

        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image JPG", "jpg"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            filename = fileChooser.getSelectedFile().getPath();
            if (!filename.endsWith(".jpg")) {
                filename += ".jpg";
            }
            ((PainelDesenho) pnlAreaTrabalho).salvar(filename);
        }
    }

    public void setCOR(String objeto, Color cor) {

        if (objeto.equals("LINHA")) {
            corLinha = cor;
            btnCorLinha.setBackground(corLinha);
        } else {
            corFundo = cor;
            btnCorFundo.setBackground(corFundo);
        }
    }

    public void limpaTela() {
        ((PainelDesenho) pnlAreaTrabalho).limpaTudo();
    }

    public void pintarObjeto() {

        BufferedImage newImage = new BufferedImage(pnlAreaTrabalho.getWidth(), pnlAreaTrabalho.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics graphics = newImage.createGraphics();

        int temp, width, height, x, y;
        String tipo = "";

        x = xi > xf ? xf : xi;
        y = yi > yf ? yf : yi;
        width = xi > xf ? xi - xf : xf - xi;
        height = yi > yf ? yi - yf : yf - yi;

        graphics.setColor(corLinha);
        System.out.println("DESENHANDO: " + itemSelecionado);

        if (itemSelecionado.equals("ABRIR")) {
            btnAbrir.setEnabled(false);
            return;
        } else if (itemSelecionado.equals("SALVAR")) {
            btnSalvar.setEnabled(false);
            return;
        } else if (itemSelecionado.equals("LAPIS")) {
            btnLapis.setEnabled(false);
            return;
        } else if (itemSelecionado.equals("TEXTO")) {
            btnTexto.setEnabled(false);
            graphics.drawString(texto, x, y);
            tipo = "TXT";
        } else if (itemSelecionado.equals("LINHA")) {
            graphics.drawLine(xi, yi, xf, yf);
            tipo = "LNH";
        } else if (itemSelecionado.equals("BORRACHA")) {
            btnBorracha.setEnabled(false);
            tipo = "BRC";
        } else if (itemSelecionado.equals("TRIANGULO")) {
            graphics.drawPolygon(getXPoints(), getYPoints(), pontos.size());
            esvaziaPontos();
            tipo = "TRL";
        } else if (itemSelecionado.equals("TRIANGULOOPACO")) {
            graphics.setColor(corFundo);
            graphics.fillPolygon(getXPoints(), getYPoints(), pontos.size());
            graphics.setColor(corLinha);
            graphics.drawPolygon(getXPoints(), getYPoints(), pontos.size());
            esvaziaPontos();
            tipo = "TRLO";
        } else if (itemSelecionado.equals("POLIGONO")) {
            graphics.drawPolygon(getXPoints(), getYPoints(), pontos.size());
            esvaziaPontos();
            tipo = "PGN";
        } else if (itemSelecionado.equals("POLIGONOOPACO")) {
            graphics.setColor(corFundo);
            graphics.fillPolygon(getXPoints(), getYPoints(), pontos.size());
            graphics.setColor(corLinha);
            graphics.drawPolygon(getXPoints(), getYPoints(), pontos.size());
            esvaziaPontos();
            tipo = "PGNO";
        } else if (itemSelecionado.equals("QUADRADO")) {
            graphics.drawRect(x, y, width, height);
            tipo = "QRD";
        } else if (itemSelecionado.equals("QUADRADOOPACO")) {
            graphics.setColor(corFundo);
            graphics.fillRect(x, y, width, height);
            graphics.setColor(corLinha);
            graphics.drawRect(x, y, width, height);
            tipo = "QRDO";
        } else if (itemSelecionado.equals("QUADRADOARREDONDADO")) {
            graphics.drawRoundRect(x, y, width, height, redondura, redondura);
            tipo = "QRR";
        } else if (itemSelecionado.equals("QUADRADOARREDONDADOOPACO")) {
            graphics.setColor(corFundo);
            graphics.fillRoundRect(x, y, width, height, redondura, redondura);
            graphics.setColor(corLinha);
            graphics.drawRoundRect(x, y, width, height, redondura, redondura);
            tipo = "QRRO";
        } else if (itemSelecionado.equals("CONTAGOTAS")) {
            btnContaGotas.setEnabled(false);
            return;
        } else if (itemSelecionado.equals("CIRCULO")) {
            graphics.drawOval(x, y, width, height);
            tipo = "CRL";
        } else if (itemSelecionado.equals("CIRCULOOPACO")) {
            graphics.setColor(corFundo);
            graphics.fillOval(x, y, width, height);
            graphics.setColor(corLinha);
            graphics.drawOval(x, y, width, height);
            tipo = "CRLO";
        } else if (itemSelecionado.equals("BALDETINTA")) {
            btnBaldeTinta.setEnabled(false);
            return;
        } else if (itemSelecionado.equals("REDIMENSIONAR")) {
            btnRedimensionar.setEnabled(false);
            return;
        } else if (itemSelecionado.equals("ROTACIONAR")) {
            btnRotacionar.setEnabled(false);
            return;
        }

        ((PainelDesenho) pnlAreaTrabalho).adicionaImagem(newImage, tipo);


    }

    public void selecionaCor(String objeto) {

        JColorChooser colorChooser = new JColorChooser();

        if (objeto.equals("LINHA")) {
            corLinha = colorChooser.showDialog(btnCorLinha, "Selecione uma cor para a linha", corLinha);
            btnCorLinha.setBackground(corLinha);
        } else {
            corFundo = colorChooser.showDialog(btnCorFundo, "Selecione uma cor para o fundo", corFundo);
            btnCorFundo.setBackground(corFundo);
        }
    }

    public void selecionaTexto() {

        final JDialog dialogTexto = new JDialog(this, "Digite o texto", true);
        dialogTexto.setSize(100, 25);

        final JTextField txtTexto = new JTextField(texto);

        JButton btnOK = new JButton("OK");

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                texto = txtTexto.getText();
                dialogTexto.dispose();
                pintarObjeto();
            }
        });

        txtTexto.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    texto = txtTexto.getText();
                    dialogTexto.dispose();
                    pintarObjeto();
                }
            }
        ;
        });  

        dialogTexto.setLayout(new GridBagLayout());

        dialogTexto.add(txtTexto);
        txtTexto.setPreferredSize(new Dimension(100, 25));
        txtTexto.setLocation(0, 0);

        dialogTexto.add(btnOK);
        btnOK.setPreferredSize(new Dimension(50, 25));
        btnOK.setLocation(100, 0);

        dialogTexto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialogTexto.pack();

        dialogTexto.setLocation(this.getX() + pnlAreaTrabalho.getX() + xf, this.getY() + pnlAreaTrabalho.getY() + yf);
        dialogTexto.setVisible(true);
        txtTexto.requestFocus();
    }

    public void selecionaRedondura() {
        final JDialog dialogRedondura = new JDialog(this, "Digite a redondura", true);
        dialogRedondura.setSize(100, 25);

        final JSpinner txtRedondura = new JSpinner(new SpinnerNumberModel(redondura, 0, 200, 1));

        JButton btnOK = new JButton("OK");

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redondura = (Integer) txtRedondura.getValue();
                dialogRedondura.dispose();
                pintarObjeto();
            }
        });

        txtRedondura.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    redondura = (Integer) txtRedondura.getValue();
                    dialogRedondura.dispose();
                    pintarObjeto();
                }
            }
        ;
        });  

        dialogRedondura.setLayout(new GridBagLayout());

        dialogRedondura.add(txtRedondura);
        txtRedondura.setPreferredSize(new Dimension(100, 25));
        txtRedondura.setLocation(0, 0);

        dialogRedondura.add(btnOK);
        btnOK.setPreferredSize(new Dimension(50, 25));
        btnOK.setLocation(100, 0);

        dialogRedondura.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialogRedondura.pack();

        dialogRedondura.setLocation(this.getX() + pnlAreaTrabalho.getX() + xf, this.getY() + pnlAreaTrabalho.getY() + yf);
        dialogRedondura.setVisible(true);
        txtRedondura.requestFocus();
    }

    public void selecionaArco() {


        final JDialog dialogTexto = new JDialog(this, "Personalize o arco", true);
        GridBagConstraints cons = new GridBagConstraints();
        dialogTexto.setPreferredSize(new Dimension(200, 150));
        dialogTexto.setLayout(new GridBagLayout());

        JLabel lblAnguloInicial = new JLabel("Angulo Inicial");
        JLabel lblAnguloArco = new JLabel("Angulo Arco");

        final JTextField txtAnguloInicial = new JTextField(String.valueOf(anguloInicial));
        final JTextField txtAnguloArco = new JTextField(String.valueOf(anguloArco));

        JButton btnOK = new JButton("OK");

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anguloArco = Integer.valueOf(txtAnguloInicial.getText());
                anguloInicial = Integer.valueOf(txtAnguloArco.getText());
                dialogTexto.dispose();
                pintarObjeto();
            }
        });

        txtAnguloArco.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    anguloArco = Integer.valueOf(txtAnguloInicial.getText());
                    anguloInicial = Integer.valueOf(txtAnguloArco.getText());
                    dialogTexto.dispose();
                    pintarObjeto();
                }
            }
        ;
        });  

        cons.fill = GridBagConstraints.BOTH;

        // angulo inicial

        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 100;
        cons.weighty = 25;
        dialogTexto.add(lblAnguloInicial, cons);

        cons.gridx = 1;
        cons.gridy = 0;
        dialogTexto.add(txtAnguloInicial, cons);

        // angulo arco

        cons.gridx = 0;
        cons.gridy = 1;
        dialogTexto.add(lblAnguloArco, cons);

        cons.gridx = 1;
        cons.gridy = 1;
        dialogTexto.add(txtAnguloArco, cons);

        // btn ok

        cons.gridx = 0;
        cons.gridy = 2;
        dialogTexto.add(btnOK, cons);

        //

        dialogTexto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialogTexto.pack();

        dialogTexto.setLocation(this.getX() + pnlAreaTrabalho.getX() + xf, this.getY() + pnlAreaTrabalho.getY() + yf);
        dialogTexto.setVisible(true);
        txtAnguloInicial.requestFocus();
    }

    public void esvaziaPontos() {
        while (pontos.size() > 0) {
            pontos.remove(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFerramentas = new javax.swing.JPanel();
        btnCirculoOpaco = new javax.swing.JButton();
        btnQuadradoOpaco = new javax.swing.JButton();
        btnLapis = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnAbrir = new javax.swing.JButton();
        btnBaldeTinta = new javax.swing.JButton();
        btnCirculo = new javax.swing.JButton();
        btnContaGotas = new javax.swing.JButton();
        btnQuadrado = new javax.swing.JButton();
        btnTexto = new javax.swing.JButton();
        btnLinha = new javax.swing.JButton();
        btnBorracha = new javax.swing.JButton();
        btnQuadradoArredondadoOpaco = new javax.swing.JButton();
        btnQuadradoArredondado = new javax.swing.JButton();
        btnCorLinha = new javax.swing.JPanel();
        lblBtnCorLinha = new javax.swing.JLabel();
        btnCorFundo = new javax.swing.JPanel();
        lblBtnCorFundo = new javax.swing.JLabel();
        btnPoligonoOpaco = new javax.swing.JButton();
        btnPoligono = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnArco = new javax.swing.JButton();
        btnTrianguloOpaco = new javax.swing.JButton();
        btnTriangulo = new javax.swing.JButton();
        scAreaTrabalho = new javax.swing.JScrollPane();
        pnlAreaTrabalho = new PainelDesenho();
        pnlRodape = new javax.swing.JPanel();
        lblRodapeInfo = new javax.swing.JLabel();
        pnlPropriedades = new javax.swing.JPanel();
        slCamadas = new javax.swing.JScrollPane();
        jlCamadas = new javax.swing.JList();
        lblCamadas = new javax.swing.JLabel();
        btnRotacionar = new javax.swing.JButton();
        btnRedimensionar = new javax.swing.JButton();
        btnMoveUp = new javax.swing.JButton();
        btnMoveDown = new javax.swing.JButton();
        btnJoin = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(413, 309));

        btnCirculoOpaco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/circulo_opaco.png"))); // NOI18N
        btnCirculoOpaco.setMaximumSize(new java.awt.Dimension(24, 24));
        btnCirculoOpaco.setMinimumSize(new java.awt.Dimension(24, 24));
        btnCirculoOpaco.setPreferredSize(new java.awt.Dimension(24, 24));
        btnCirculoOpaco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCirculoOpacoActionPerformed(evt);
            }
        });

        btnQuadradoOpaco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/quadrado_opaco.png"))); // NOI18N
        btnQuadradoOpaco.setMaximumSize(new java.awt.Dimension(24, 24));
        btnQuadradoOpaco.setMinimumSize(new java.awt.Dimension(24, 24));
        btnQuadradoOpaco.setPreferredSize(new java.awt.Dimension(24, 24));
        btnQuadradoOpaco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuadradoOpacoActionPerformed(evt);
            }
        });

        btnLapis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/pencil.png"))); // NOI18N
        btnLapis.setMaximumSize(new java.awt.Dimension(24, 24));
        btnLapis.setMinimumSize(new java.awt.Dimension(24, 24));
        btnLapis.setPreferredSize(new java.awt.Dimension(24, 24));
        btnLapis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapisActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/salvar.png"))); // NOI18N
        btnSalvar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnSalvar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnSalvar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/abrir.png"))); // NOI18N
        btnAbrir.setMaximumSize(new java.awt.Dimension(24, 24));
        btnAbrir.setMinimumSize(new java.awt.Dimension(24, 24));
        btnAbrir.setPreferredSize(new java.awt.Dimension(24, 24));
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnBaldeTinta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/pintar.png"))); // NOI18N
        btnBaldeTinta.setEnabled(false);
        btnBaldeTinta.setMaximumSize(new java.awt.Dimension(24, 24));
        btnBaldeTinta.setMinimumSize(new java.awt.Dimension(24, 24));
        btnBaldeTinta.setPreferredSize(new java.awt.Dimension(24, 24));
        btnBaldeTinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaldeTintaActionPerformed(evt);
            }
        });

        btnCirculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/circulo.png"))); // NOI18N
        btnCirculo.setMaximumSize(new java.awt.Dimension(24, 24));
        btnCirculo.setMinimumSize(new java.awt.Dimension(24, 24));
        btnCirculo.setPreferredSize(new java.awt.Dimension(24, 24));
        btnCirculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCirculoActionPerformed(evt);
            }
        });

        btnContaGotas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/contagotas.png"))); // NOI18N
        btnContaGotas.setEnabled(false);
        btnContaGotas.setMaximumSize(new java.awt.Dimension(24, 24));
        btnContaGotas.setMinimumSize(new java.awt.Dimension(24, 24));
        btnContaGotas.setPreferredSize(new java.awt.Dimension(24, 24));
        btnContaGotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaGotasActionPerformed(evt);
            }
        });

        btnQuadrado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/quadrado.png"))); // NOI18N
        btnQuadrado.setMaximumSize(new java.awt.Dimension(24, 24));
        btnQuadrado.setMinimumSize(new java.awt.Dimension(24, 24));
        btnQuadrado.setPreferredSize(new java.awt.Dimension(24, 24));
        btnQuadrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuadradoActionPerformed(evt);
            }
        });

        btnTexto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/texto.png"))); // NOI18N
        btnTexto.setMaximumSize(new java.awt.Dimension(24, 24));
        btnTexto.setMinimumSize(new java.awt.Dimension(24, 24));
        btnTexto.setPreferredSize(new java.awt.Dimension(24, 24));
        btnTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTextoActionPerformed(evt);
            }
        });

        btnLinha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/linha.png"))); // NOI18N
        btnLinha.setMaximumSize(new java.awt.Dimension(24, 24));
        btnLinha.setMinimumSize(new java.awt.Dimension(24, 24));
        btnLinha.setPreferredSize(new java.awt.Dimension(24, 24));
        btnLinha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLinhaActionPerformed(evt);
            }
        });

        btnBorracha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/borracha.png"))); // NOI18N
        btnBorracha.setMaximumSize(new java.awt.Dimension(24, 24));
        btnBorracha.setMinimumSize(new java.awt.Dimension(24, 24));
        btnBorracha.setPreferredSize(new java.awt.Dimension(24, 24));
        btnBorracha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrachaActionPerformed(evt);
            }
        });

        btnQuadradoArredondadoOpaco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/quadradoarredondadoopaco.png"))); // NOI18N
        btnQuadradoArredondadoOpaco.setMaximumSize(new java.awt.Dimension(24, 24));
        btnQuadradoArredondadoOpaco.setMinimumSize(new java.awt.Dimension(24, 24));
        btnQuadradoArredondadoOpaco.setPreferredSize(new java.awt.Dimension(24, 24));
        btnQuadradoArredondadoOpaco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuadradoArredondadoOpacoActionPerformed(evt);
            }
        });

        btnQuadradoArredondado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/quadradoarredondado.png"))); // NOI18N
        btnQuadradoArredondado.setMaximumSize(new java.awt.Dimension(24, 24));
        btnQuadradoArredondado.setMinimumSize(new java.awt.Dimension(24, 24));
        btnQuadradoArredondado.setPreferredSize(new java.awt.Dimension(24, 24));
        btnQuadradoArredondado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuadradoArredondadoActionPerformed(evt);
            }
        });

        btnCorLinha.setBackground(new java.awt.Color(0, 0, 0));
        btnCorLinha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCorLinha.setPreferredSize(new java.awt.Dimension(54, 20));
        btnCorLinha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCorLinhaMouseClicked(evt);
            }
        });

        lblBtnCorLinha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBtnCorLinha.setText("Linha");
        lblBtnCorLinha.setEnabled(false);

        javax.swing.GroupLayout btnCorLinhaLayout = new javax.swing.GroupLayout(btnCorLinha);
        btnCorLinha.setLayout(btnCorLinhaLayout);
        btnCorLinhaLayout.setHorizontalGroup(
            btnCorLinhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCorLinhaLayout.createSequentialGroup()
                .addComponent(lblBtnCorLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        btnCorLinhaLayout.setVerticalGroup(
            btnCorLinhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBtnCorLinha, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        btnCorFundo.setBackground(new java.awt.Color(135, 135, 135));
        btnCorFundo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCorFundo.setPreferredSize(new java.awt.Dimension(54, 20));
        btnCorFundo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCorFundoMouseClicked(evt);
            }
        });

        lblBtnCorFundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBtnCorFundo.setText("Fundo");
        lblBtnCorFundo.setEnabled(false);
        lblBtnCorFundo.setMaximumSize(new java.awt.Dimension(30, 20));
        lblBtnCorFundo.setMinimumSize(new java.awt.Dimension(30, 20));
        lblBtnCorFundo.setPreferredSize(new java.awt.Dimension(30, 20));

        javax.swing.GroupLayout btnCorFundoLayout = new javax.swing.GroupLayout(btnCorFundo);
        btnCorFundo.setLayout(btnCorFundoLayout);
        btnCorFundoLayout.setHorizontalGroup(
            btnCorFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBtnCorFundo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );
        btnCorFundoLayout.setVerticalGroup(
            btnCorFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCorFundoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblBtnCorFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnPoligonoOpaco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/poligonoopaco.png"))); // NOI18N
        btnPoligonoOpaco.setMaximumSize(new java.awt.Dimension(24, 24));
        btnPoligonoOpaco.setMinimumSize(new java.awt.Dimension(24, 24));
        btnPoligonoOpaco.setPreferredSize(new java.awt.Dimension(24, 24));
        btnPoligonoOpaco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoligonoOpacoActionPerformed(evt);
            }
        });

        btnPoligono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/poligono.png"))); // NOI18N
        btnPoligono.setMaximumSize(new java.awt.Dimension(24, 24));
        btnPoligono.setMinimumSize(new java.awt.Dimension(24, 24));
        btnPoligono.setPreferredSize(new java.awt.Dimension(24, 24));
        btnPoligono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoligonoActionPerformed(evt);
            }
        });

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/clear.png"))); // NOI18N
        btnClear.setMaximumSize(new java.awt.Dimension(24, 24));
        btnClear.setMinimumSize(new java.awt.Dimension(24, 24));
        btnClear.setPreferredSize(new java.awt.Dimension(24, 24));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnArco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/arco.png"))); // NOI18N
        btnArco.setMaximumSize(new java.awt.Dimension(24, 24));
        btnArco.setMinimumSize(new java.awt.Dimension(24, 24));
        btnArco.setPreferredSize(new java.awt.Dimension(24, 24));
        btnArco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArcoActionPerformed(evt);
            }
        });

        btnTrianguloOpaco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/trianguloopaco.png"))); // NOI18N
        btnTrianguloOpaco.setMaximumSize(new java.awt.Dimension(24, 24));
        btnTrianguloOpaco.setMinimumSize(new java.awt.Dimension(24, 24));
        btnTrianguloOpaco.setPreferredSize(new java.awt.Dimension(24, 24));
        btnTrianguloOpaco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrianguloOpacoActionPerformed(evt);
            }
        });

        btnTriangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/triangulo.png"))); // NOI18N
        btnTriangulo.setMaximumSize(new java.awt.Dimension(24, 24));
        btnTriangulo.setMinimumSize(new java.awt.Dimension(24, 24));
        btnTriangulo.setPreferredSize(new java.awt.Dimension(24, 24));
        btnTriangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrianguloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFerramentasLayout = new javax.swing.GroupLayout(pnlFerramentas);
        pnlFerramentas.setLayout(pnlFerramentasLayout);
        pnlFerramentasLayout.setHorizontalGroup(
            pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFerramentasLayout.createSequentialGroup()
                        .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnQuadradoArredondado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuadradoArredondadoOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnArco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnPoligono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPoligonoOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnTriangulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTrianguloOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnCirculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCirculoOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnQuadrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuadradoOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnLinha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBorracha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnLapis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCorLinha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFerramentasLayout.createSequentialGroup()
                        .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCorFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                                .addComponent(btnBaldeTinta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnContaGotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlFerramentasLayout.setVerticalGroup(
            pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFerramentasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLapis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLinha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorracha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuadrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuadradoOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuadradoArredondado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuadradoArredondadoOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCirculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCirculoOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTriangulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTrianguloOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPoligono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPoligonoOpaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnArco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCorLinha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCorFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBaldeTinta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContaGotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        pnlAreaTrabalho.setBackground(new java.awt.Color(255, 255, 255));
        pnlAreaTrabalho.setPreferredSize(new java.awt.Dimension(320, 240));
        pnlAreaTrabalho.setRequestFocusEnabled(false);
        pnlAreaTrabalho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlAreaTrabalhoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlAreaTrabalhoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlAreaTrabalhoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlAreaTrabalhoMouseReleased(evt);
            }
        });
        pnlAreaTrabalho.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlAreaTrabalhoMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                pnlAreaTrabalhoMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout pnlAreaTrabalhoLayout = new javax.swing.GroupLayout(pnlAreaTrabalho);
        pnlAreaTrabalho.setLayout(pnlAreaTrabalhoLayout);
        pnlAreaTrabalhoLayout.setHorizontalGroup(
            pnlAreaTrabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
        pnlAreaTrabalhoLayout.setVerticalGroup(
            pnlAreaTrabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );

        scAreaTrabalho.setViewportView(pnlAreaTrabalho);

        lblRodapeInfo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRodapeInfo.setText("ae");

        javax.swing.GroupLayout pnlRodapeLayout = new javax.swing.GroupLayout(pnlRodape);
        pnlRodape.setLayout(pnlRodapeLayout);
        pnlRodapeLayout.setHorizontalGroup(
            pnlRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRodapeLayout.createSequentialGroup()
                .addComponent(lblRodapeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlRodapeLayout.setVerticalGroup(
            pnlRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRodapeLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblRodapeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlPropriedades.setMaximumSize(new java.awt.Dimension(134, 234));
        pnlPropriedades.setMinimumSize(new java.awt.Dimension(134, 234));

        jlCamadas.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        slCamadas.setViewportView(jlCamadas);

        lblCamadas.setText("Camadas");

        btnRotacionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/rotacionar.png"))); // NOI18N
        btnRotacionar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnRotacionar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnRotacionar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnRotacionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRotacionarActionPerformed(evt);
            }
        });

        btnRedimensionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/redimensionar.png"))); // NOI18N
        btnRedimensionar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnRedimensionar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnRedimensionar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnRedimensionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedimensionarActionPerformed(evt);
            }
        });

        btnMoveUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/up.png"))); // NOI18N
        btnMoveUp.setMaximumSize(new java.awt.Dimension(24, 24));
        btnMoveUp.setMinimumSize(new java.awt.Dimension(24, 24));
        btnMoveUp.setPreferredSize(new java.awt.Dimension(24, 24));
        btnMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveUpActionPerformed(evt);
            }
        });

        btnMoveDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/down.png"))); // NOI18N
        btnMoveDown.setMaximumSize(new java.awt.Dimension(24, 24));
        btnMoveDown.setMinimumSize(new java.awt.Dimension(24, 24));
        btnMoveDown.setPreferredSize(new java.awt.Dimension(24, 24));
        btnMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveDownActionPerformed(evt);
            }
        });

        btnJoin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/join.png"))); // NOI18N
        btnJoin.setMaximumSize(new java.awt.Dimension(24, 24));
        btnJoin.setMinimumSize(new java.awt.Dimension(24, 24));
        btnJoin.setPreferredSize(new java.awt.Dimension(24, 24));
        btnJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/resources/icones/delete.png"))); // NOI18N
        btnDelete.setMaximumSize(new java.awt.Dimension(24, 24));
        btnDelete.setMinimumSize(new java.awt.Dimension(24, 24));
        btnDelete.setPreferredSize(new java.awt.Dimension(24, 24));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPropriedadesLayout = new javax.swing.GroupLayout(pnlPropriedades);
        pnlPropriedades.setLayout(pnlPropriedadesLayout);
        pnlPropriedadesLayout.setHorizontalGroup(
            pnlPropriedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPropriedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPropriedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slCamadas, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlPropriedadesLayout.createSequentialGroup()
                        .addGroup(pnlPropriedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCamadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPropriedadesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(pnlPropriedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlPropriedadesLayout.createSequentialGroup()
                                        .addComponent(btnMoveUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnMoveDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnJoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPropriedadesLayout.createSequentialGroup()
                                        .addComponent(btnRedimensionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnRotacionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        pnlPropriedadesLayout.setVerticalGroup(
            pnlPropriedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPropriedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCamadas, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slCamadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPropriedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMoveUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoveDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPropriedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRedimensionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRotacionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(147, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlFerramentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scAreaTrabalho, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlPropriedades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlRodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFerramentas, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pnlPropriedades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scAreaTrabalho))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlRodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        System.out.println("ABRIR");
        carregarArquivo();
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        System.out.println("SALVAR");
        salvaArquivo();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLapisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapisActionPerformed
        selecionaItemPaleta("LAPIS");
        this.cursorSelecionado = cursorLapis;
    }//GEN-LAST:event_btnLapisActionPerformed

    private void btnTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTextoActionPerformed
        selecionaItemPaleta("TEXTO");
        this.cursorSelecionado = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
    }//GEN-LAST:event_btnTextoActionPerformed

    private void btnLinhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLinhaActionPerformed
        selecionaItemPaleta("LINHA");
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnLinhaActionPerformed

    private void btnBorrachaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrachaActionPerformed
        selecionaItemPaleta("BORRACHA");
        this.cursorSelecionado = cursorQuadrado;
    }//GEN-LAST:event_btnBorrachaActionPerformed

    private void btnQuadradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuadradoActionPerformed
        selecionaItemPaleta("QUADRADO");
        this.cursorSelecionado = cursorMira;

    }//GEN-LAST:event_btnQuadradoActionPerformed

    private void btnContaGotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaGotasActionPerformed
        selecionaItemPaleta("CONTAGOTAS");
        this.cursorSelecionado = cursorContaGotas;
    }//GEN-LAST:event_btnContaGotasActionPerformed

    private void btnCirculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCirculoActionPerformed
        selecionaItemPaleta("CIRCULO");
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnCirculoActionPerformed

    private void btnBaldeTintaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaldeTintaActionPerformed
        selecionaItemPaleta("BALDETINTA");
        this.cursorSelecionado = cursorPintar;
    }//GEN-LAST:event_btnBaldeTintaActionPerformed

    private void btnRedimensionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedimensionarActionPerformed
        if (jlCamadas.getSelectedIndices().length > 0) {
            ((PainelDesenho) pnlAreaTrabalho).transform(jlCamadas.getSelectedIndices(), "SCALE", 0.9, 0.9, 0);
        }
    }//GEN-LAST:event_btnRedimensionarActionPerformed

    private void btnRotacionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRotacionarActionPerformed
        if (jlCamadas.getSelectedIndices().length > 0) {
            ((PainelDesenho) pnlAreaTrabalho).transform(jlCamadas.getSelectedIndices(), "ROTATE", 0, 0, 15);
        }
    }//GEN-LAST:event_btnRotacionarActionPerformed

    private void pnlAreaTrabalhoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAreaTrabalhoMouseEntered
        this.setCursor(cursorSelecionado);
    }//GEN-LAST:event_pnlAreaTrabalhoMouseEntered

    private void pnlAreaTrabalhoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAreaTrabalhoMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_pnlAreaTrabalhoMouseExited

    private void pnlAreaTrabalhoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAreaTrabalhoMouseMoved
        rodapeCordenada = "Mouse [" + evt.getX() + ", " + evt.getY() + "]";
        atualizaRodapeInfo();
    }//GEN-LAST:event_pnlAreaTrabalhoMouseMoved

    private void pnlAreaTrabalhoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAreaTrabalhoMousePressed
        System.out.println("Mouse pressed: [" + evt.getX() + ", " + evt.getY() + "]");
        drawing = true;
        xi = evt.getX();
        yi = evt.getY();
    }//GEN-LAST:event_pnlAreaTrabalhoMousePressed

    private void pnlAreaTrabalhoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAreaTrabalhoMouseReleased

        System.out.println("Mouse released: [" + evt.getX() + ", " + evt.getY() + "]");

        if (evt.getButton() == MouseEvent.BUTTON1) { // botão esquerdo

            xf = evt.getX();
            yf = evt.getY();

            if (itemSelecionado.equals("TEXTO")) {
                drawing = false;
                selecionaTexto();
            } else if (itemSelecionado.equals("QUADRADOARREDONDADO") || itemSelecionado.equals("QUADRADOARREDONDADOOPACO")) {
                drawing = false;
                selecionaRedondura();
            } else if (itemSelecionado.equals("ARCO")) {
                drawing = false;
                selecionaArco();
            } else if ((itemSelecionado.equals("TRIANGULO") || itemSelecionado.equals("TRIANGULOOPACO")) && pontos.size() < 3) {
                pontos.add(new Point(evt.getX(), evt.getY()));
                if (pontos.size() == 3) {
                    drawing = false;
                    msgInformacao = "";
                    atualizaRodapeInfo();
                    pintarObjeto();
                } else {
                    msgInformacao = "Clique em " + (3 - pontos.size()) + " pontos na tela para formar o triangulo";
                    atualizaRodapeInfo();
                }
            } else if (itemSelecionado.equals("POLIGONO") || itemSelecionado.equals("POLIGONOOPACO")) {
                pontos.add(new Point(evt.getX(), evt.getY()));
                if (drawing == false) {
                    msgInformacao = "";
                    atualizaRodapeInfo();
                    pintarObjeto();
                } else {
                    msgInformacao = "Vá clicando para formar os pontos do poligono, você já tem " + pontos.size() + " pontos [PRESSIONE <ENTER> PARA DESENHAR]";
                    atualizaRodapeInfo();
                }

            } else {
                drawing = false;
                pintarObjeto();
            }

        } else if (evt.getButton() == MouseEvent.BUTTON3) { // botão direito

            if (itemSelecionado.equals("POLIGONO") || itemSelecionado.equals("POLIGONOOPACO")) {
                drawing = false;
                msgInformacao = "";
                atualizaRodapeInfo();
                pintarObjeto();
            }
        }


    }//GEN-LAST:event_pnlAreaTrabalhoMouseReleased

    private void btnCorLinhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCorLinhaMouseClicked
        selecionaCor("LINHA");
    }//GEN-LAST:event_btnCorLinhaMouseClicked

    private void btnCorFundoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCorFundoMouseClicked
        selecionaCor("FUNDO");
    }//GEN-LAST:event_btnCorFundoMouseClicked

    private void btnQuadradoOpacoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuadradoOpacoActionPerformed
        selecionaItemPaleta("QUADRADOOPACO");
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnQuadradoOpacoActionPerformed

    private void btnCirculoOpacoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCirculoOpacoActionPerformed
        selecionaItemPaleta("CIRCULOOPACO");
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnCirculoOpacoActionPerformed

    private void pnlAreaTrabalhoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAreaTrabalhoMouseDragged

        BufferedImage newImage = new BufferedImage(pnlAreaTrabalho.getWidth(), pnlAreaTrabalho.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics graphics = newImage.createGraphics();

        if (itemSelecionado.equals("BORRACHA") && drawing == true) {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(evt.getX(), evt.getY(), 20, 20);
            ((PainelDesenho) pnlAreaTrabalho).adicionaImagem(newImage, "BRC");
        } else if (itemSelecionado.equals("LAPIS") && drawing == true) {
            graphics.setColor(corLinha);
            graphics.fillRect(evt.getX(), evt.getY(), 3, 3);
            ((PainelDesenho) pnlAreaTrabalho).adicionaImagem(newImage, "LPS");
        }

    }//GEN-LAST:event_pnlAreaTrabalhoMouseDragged

    private void btnQuadradoArredondadoOpacoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuadradoArredondadoOpacoActionPerformed
        selecionaItemPaleta("QUADRADOARREDONDADOOPACO");
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnQuadradoArredondadoOpacoActionPerformed

    private void btnQuadradoArredondadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuadradoArredondadoActionPerformed
        selecionaItemPaleta("QUADRADOARREDONDADO");
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnQuadradoArredondadoActionPerformed

    private void btnPoligonoOpacoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoligonoOpacoActionPerformed
        selecionaItemPaleta("POLIGONOOPACO");
        msgInformacao = "Clique para selecionar os pontos, para formar os pontos do poligono, você já tem " + pontos.size() + " pontos [PRESSIONE <ENTER> PARA DESENHAR]";
        atualizaRodapeInfo();
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnPoligonoOpacoActionPerformed

    private void btnPoligonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoligonoActionPerformed
        selecionaItemPaleta("POLIGONO");
        msgInformacao = "Vá clicando para formar os pontos do poligono, você já tem " + pontos.size() + " pontos [PRESSIONE <ENTER> PARA DESENHAR]";
        atualizaRodapeInfo();
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnPoligonoActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        limpaTela();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnArcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArcoActionPerformed
        selecionaItemPaleta("ARCO");
        this.cursorSelecionado = cursorMira;
    }//GEN-LAST:event_btnArcoActionPerformed

    private void btnTrianguloOpacoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrianguloOpacoActionPerformed
        selecionaItemPaleta("TRIANGULOOPACO");
        this.cursorSelecionado = cursorMira;
        msgInformacao = "Clique em 3 pontos na tela para formar o triangulo";
        atualizaRodapeInfo();
    }//GEN-LAST:event_btnTrianguloOpacoActionPerformed

    private void btnTrianguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrianguloActionPerformed
        selecionaItemPaleta("TRIANGULO");
        this.cursorSelecionado = cursorMira;
        msgInformacao = "Clique em 3 pontos na tela para formar o triangulo";
        atualizaRodapeInfo();
    }//GEN-LAST:event_btnTrianguloActionPerformed

    private void btnMoveUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveUpActionPerformed
        if (jlCamadas.getSelectedIndices().length > 0) {
            ((PainelDesenho) pnlAreaTrabalho).moveUp(jlCamadas.getSelectedIndices());
        }
    }//GEN-LAST:event_btnMoveUpActionPerformed

    private void btnMoveDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveDownActionPerformed
        if (jlCamadas.getSelectedIndices().length > 0) {
            ((PainelDesenho) pnlAreaTrabalho).moveDown(jlCamadas.getSelectedIndices());
        }    }//GEN-LAST:event_btnMoveDownActionPerformed

    private void btnJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinActionPerformed
        if (jlCamadas.getSelectedIndices().length > 0) {
            ((PainelDesenho) pnlAreaTrabalho).join(jlCamadas.getSelectedIndices());
        }
    }//GEN-LAST:event_btnJoinActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (jlCamadas.getSelectedIndices().length > 0) {
            ((PainelDesenho) pnlAreaTrabalho).remove(jlCamadas.getSelectedIndices());
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaintD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaintD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaintD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaintD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaintD().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnArco;
    private javax.swing.JButton btnBaldeTinta;
    private javax.swing.JButton btnBorracha;
    private javax.swing.JButton btnCirculo;
    private javax.swing.JButton btnCirculoOpaco;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnContaGotas;
    private javax.swing.JPanel btnCorFundo;
    private javax.swing.JPanel btnCorLinha;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnJoin;
    private javax.swing.JButton btnLapis;
    private javax.swing.JButton btnLinha;
    private javax.swing.JButton btnMoveDown;
    private javax.swing.JButton btnMoveUp;
    private javax.swing.JButton btnPoligono;
    private javax.swing.JButton btnPoligonoOpaco;
    private javax.swing.JButton btnQuadrado;
    private javax.swing.JButton btnQuadradoArredondado;
    private javax.swing.JButton btnQuadradoArredondadoOpaco;
    private javax.swing.JButton btnQuadradoOpaco;
    private javax.swing.JButton btnRedimensionar;
    private javax.swing.JButton btnRotacionar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnTexto;
    private javax.swing.JButton btnTriangulo;
    private javax.swing.JButton btnTrianguloOpaco;
    private javax.swing.JList jlCamadas;
    private javax.swing.JLabel lblBtnCorFundo;
    private javax.swing.JLabel lblBtnCorLinha;
    private javax.swing.JLabel lblCamadas;
    private javax.swing.JLabel lblRodapeInfo;
    private javax.swing.JPanel pnlAreaTrabalho;
    private javax.swing.JPanel pnlFerramentas;
    private javax.swing.JPanel pnlPropriedades;
    private javax.swing.JPanel pnlRodape;
    private javax.swing.JScrollPane scAreaTrabalho;
    private javax.swing.JScrollPane slCamadas;
    // End of variables declaration//GEN-END:variables
}

class PainelDesenho extends JPanel {

    public ArrayList<BufferedImage> imagens;
    DefaultListModel listModel;

    public PainelDesenho() {
        super();
        listModel = new DefaultListModel();
    }

    public DefaultListModel getDefaultListModel() {
        return this.listModel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagens != null && imagens.size() != 0) {
            for (BufferedImage imagem : imagens) {
                g.drawImage(imagem, 0, 0, null);
            }
        }
    }

    public void adicionaImagem(BufferedImage imagem, String nome) {
        if (imagens == null) {
            imagens = new ArrayList<BufferedImage>();
        }

        imagens.add(imagem);
        listModel.addElement(nome);
        this.repaint();

    }

    public int qtdeElementos(String tipo) {

        int qtde = 0;

        for (int i = 0; i < listModel.size(); i++) {
            if (((String) listModel.getElementAt(i)).contains(tipo)) {
                qtde++;
            }
        }

        return qtde;
    }

    public void limpaTudo() {
        imagens.clear();
        listModel.clear();
    }

    public void joinLastType() {
    }

    public int[] moveIndexes(int[] indexes, int incr) {
        for (int i = 0; i < indexes.length; i++) {
            if ((indexes[i] += incr) >= 0 && (indexes[i] += incr) <= indexes.length) {
                indexes[i] += incr;
            }
        }
        return indexes;
    }

    public void moveUp(int[] indexes) {

        ArrayList<BufferedImage> removerB = new ArrayList<BufferedImage>();
        ArrayList<Object> removerL = new ArrayList<Object>();

        for (int j = indexes.length; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                if (indexes[i] != 0 && indexes[i] != listModel.size()) {

                    Collections.swap(imagens, indexes[i], indexes[i] - 1);

                    Object aObject = listModel.getElementAt(indexes[i]);
                    Object bObject = listModel.getElementAt(indexes[i] - 1);
                    listModel.set(indexes[i], bObject);
                    listModel.set(indexes[i] - 1, aObject);
                }
            }
        }

        this.repaint();
    }

    public void moveDown(int[] indexes) {

        ArrayList<BufferedImage> removerB = new ArrayList<BufferedImage>();
        ArrayList<Object> removerL = new ArrayList<Object>();

        for (int j = indexes.length; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                if (indexes[i] != 0 && indexes[i] != listModel.size()) {

                    Collections.swap(imagens, indexes[i], indexes[i] + 1);

                    Object aObject = listModel.getElementAt(indexes[i]);
                    Object bObject = listModel.getElementAt(indexes[i] + 1);
                    listModel.set(indexes[i], bObject);
                    listModel.set(indexes[i] + 1, aObject);
                }
            }
        }

        this.repaint();
    }

    public void remove(int[] indexes) {

        ArrayList<BufferedImage> removerB = new ArrayList<BufferedImage>();
        ArrayList<Object> removerL = new ArrayList<Object>();

        for (int i = 0; i < indexes.length; i++) {
            removerB.add(imagens.get(indexes[i]));
            removerL.add(listModel.get(indexes[i]));
        }

        for (BufferedImage image : removerB) {
            imagens.remove(image);
        }

        for (Object obj : removerL) {
            listModel.removeElement(obj);
        }


        this.repaint();
    }

    public void transform(int[] indexes, String transformacao, double x, double y, double graus) {

        for (int i = 0; i < indexes.length; i++) {

            BufferedImage imagem = imagens.get(indexes[i]);
            AffineTransform tx = new AffineTransform();

            if (transformacao.equals("SCALE")) {
                System.out.println("SCALE");
                tx.scale(x, y);
            } else if (transformacao.equals("SHEAR")) {
                System.out.println("SHEAR");
                tx.shear(x, y);
            } else if (transformacao.equals("TRANSLATE")) {
                System.out.println("TRANSLATE");
                tx.translate(x, y);
            } else if (transformacao.equals("ROTATE")) {
                System.out.println("ROTATE");
                tx.rotate(Math.toRadians(graus), imagem.getWidth() / 2, imagem.getHeight() / 2);
            }



            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            imagens.set(indexes[i], op.filter(imagem, null));
        }


        this.repaint();
    }

    public void join(int[] indexes) {

        BufferedImage newImage;
        Graphics g;
        int width, height;
        width = height = 0;

        ArrayList<BufferedImage> bfImagens = new ArrayList<BufferedImage>();
        ArrayList<Object> listObjects = new ArrayList<Object>();

        for (int i = 0; i < indexes.length; i++) {

            bfImagens.add(imagens.get(indexes[i]));
            listObjects.add(listModel.get(indexes[i]));

            if (imagens.get(indexes[i]).getWidth() > width) {
                width = imagens.get(indexes[i]).getWidth();
            }

            if (imagens.get(indexes[i]).getHeight() > height) {
                height = imagens.get(indexes[i]).getHeight();
            }
        }

        newImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        g = newImage.createGraphics();

        // adiciona a uma nova camada juntando os desenhos preselecionados
        for (int i = 0; i < indexes.length; i++) {
            g.drawImage(imagens.get(indexes[i]), 0, 0, null);
        }

        // removemos as imagens 
        this.remove(indexes);

        // adicionamos a nova e repintamos
        imagens.add(newImage);
        listModel.addElement("JOINED");
        this.repaint();
    }

    public void salvar(String filename) {

        BufferedImage bImage = new BufferedImage(this.getSize().width, this.getSize().height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        // tell the panel to draw its content to the new image
        this.paint(bImage.createGraphics());
        // save everything
        try {
            File imageFile = new java.io.File(filename);
            imageFile.createNewFile();
            ImageIO.write(bImage, "jpg", imageFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
