package br.saogeraldo.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import br.saogeraldo.util.FabricaConexao;
import br.saogeraldo.util.Mensagem;
import br.saogeraldo.util.RegraDeNegocioException;
import br.saogeraldo.util.ValidacaoException;

public class ArquivoBO {
	
	public static final String EXTENSION_FILE = ".script";
	private static final String FILE_PATH = FabricaConexao.caminhoBD+EXTENSION_FILE; 

	public void importarArquivo(File arquivoSelecionado) throws ValidacaoException, IOException{
		FileOutputStream outputStream = null;
		try{
			valida(arquivoSelecionado);
			FabricaConexao.close();
			byte[] bytesFile = IOUtils.toByteArray(new FileInputStream(arquivoSelecionado));
			outputStream = new FileOutputStream(FILE_PATH);
			IOUtils.write(bytesFile, outputStream);
		}finally{
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	public void exportarArquivo(File arquivo) throws RegraDeNegocioException, ValidacaoException, IOException{
		FileOutputStream outputStream = null;
		try{
			if(!arquivo.canWrite()){
				throw new ValidacaoException(Mensagem.PASTA_SEM_PERMISSAO_ESCRITA);
			}
			FabricaConexao.close();
			byte[] bytesFile = IOUtils.toByteArray(new FileInputStream(FILE_PATH));
			outputStream = new FileOutputStream(arquivo);
			IOUtils.write(bytesFile, outputStream);
		}finally{
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	private void valida(File arquivo) throws ValidacaoException{
		if(!arquivo.exists() || !arquivo.canRead()){
			throw new ValidacaoException(Mensagem.ARQUIVO_INEXISTENTE);
		}
		if (!arquivo.getName().toLowerCase().endsWith(EXTENSION_FILE)) {
			throw new ValidacaoException(String.format(Mensagem.ARQUIVO_EXTENSAO, EXTENSION_FILE));
		}
	}
}
