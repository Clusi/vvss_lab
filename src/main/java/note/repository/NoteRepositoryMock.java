package note.repository;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import note.utils.ClasaException;
import note.utils.Constants;

import note.model.Nota;

public class NoteRepositoryMock implements NoteRepository {
    private List<Nota> note;
    private String fileName;

    public NoteRepositoryMock(String fileName) {

        note = new LinkedList<Nota>();
        this.fileName = fileName;
    }


    @Override
    public void addNota(Nota nota) throws ClasaException {
        // TODO Auto-generated method stub
        if (!validareNota(nota))
            return;
        note.add(nota);
        saveNoteToFile();
    }

    private void saveNoteToFile() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            for (Nota nota : note) {
                String line = nota.getNrmatricol() + ";" + nota.getMaterie() + ";" + nota.getNota();
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean validareNota(Nota nota) throws ClasaException {
        // TODO Auto-generated method stub
        if (nota.getMaterie().length() < 5 || nota.getMaterie().length() > 20)
            throw new ClasaException(Constants.invalidMateria);
        if (nota.getNrmatricol() < Constants.minNrmatricol || nota.getNrmatricol() > Constants.maxNrmatricol)
            throw new ClasaException(Constants.invalidNrmatricol);
        if (nota.getNota() < Constants.minNota || nota.getNota() > Constants.maxNota)
            throw new ClasaException(Constants.invalidNota);
        if (nota.getNota() != (int) nota.getNota())
            throw new ClasaException(Constants.invalidNota);
        if (nota.getNrmatricol() != (int) nota.getNrmatricol())
            throw new ClasaException(Constants.invalidNrmatricol);
        return true;
    }

    @Override
    public List<Nota> getNote() {
        // TODO Auto-generated method stub
        return note;
    }

    public void readNote(String fisier) {
        try {
            FileInputStream fstream = new FileInputStream(fisier);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Nota nota = new Nota(Double.parseDouble(values[0]), values[1], Double.parseDouble(values[2]));
                note.add(nota);
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
