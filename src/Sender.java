import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Sender implements Runnable {
    private final PropertyChangeSupport support;
    private int updatedValue;

    public Sender() {
        this.support = new PropertyChangeSupport(this);
        this.updatedValue = 0;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setUpdate(int value) {
        support.firePropertyChange("progress", this.updatedValue, value);
        this.updatedValue = value;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            setUpdate(i);
            //System.out.println(i);
            try {
                Thread.sleep(50); // Simülasyon amaçlı
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // İş tamamlandığında "done" bildirimi gönderilir
        support.firePropertyChange("done", null, null);
    }
}