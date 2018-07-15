package sound;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Synthesizer;

public class Sounds implements Runnable {

	Thread t;

	Patch patch;

	public static void main(String args[]) {
		Sounds sounds = new Sounds();

		sounds.start();

		
	}
	
	public void start() {
		t = new Thread(this);
		t.start();
	}

	private static void song1() {
		try {
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();

			Instrument[] instr = midiSynth.getAvailableInstruments();
			MidiChannel[] mChannels = midiSynth.getChannels();
			MidiChannel[] mChannels1 = midiSynth.getChannels();

			midiSynth.loadInstrument(instr[10]);
			
			playNote(mChannels, 70, 100, 100);
			playNote(mChannels1, 80, 100, 100);
			

		} catch (MidiUnavailableException e) {

			e.printStackTrace();
		}
	}

	private static void playNote(MidiChannel[] mChannels, int note, int volume, int duration) {
		mChannels[0].noteOn(note, volume);
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			mChannels[0].noteOff(note);
		}
	}

	@Override
	public void run() {
		song1();
	}
}
