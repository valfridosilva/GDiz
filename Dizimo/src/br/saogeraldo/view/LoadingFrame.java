package br.saogeraldo.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LoadingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel label;

	public LoadingFrame() {

		setUndecorated(true);

		setLayout(new BorderLayout());

		ImageIcon loadingIcon = new ImageIcon(LoadingFrame.class.getClassLoader().getResource("imagens/loading.gif"));

		label = new JLabel("Gerando relatório...", loadingIcon, SwingConstants.CENTER);

		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);

		label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		add(label, BorderLayout.CENTER);

		setSize(400, 200);

		setLocationRelativeTo(null);
	}


	public void showLoading() {
		setVisible(true);
	}

	public void closeLoading() {
		dispose();
	}
}
