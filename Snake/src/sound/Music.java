package sound;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Synthesizer;

public class Music implements Runnable {
	
	Thread t;
	private static int delay = 180;
	private static int volume = 100;
	Patch patch;
	
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

			midiSynth.loadInstrument(instr[10]);

			while (true) {
				mChannels[0].noteOn(70, volume);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					mChannels[0].noteOff(70);
				}

				try {
					Thread.sleep(delay/3);
				} catch (InterruptedException e) {

				}

				mChannels[0].noteOn(71, volume);
				try {
					Thread.sleep(delay*2);
				} catch (InterruptedException e) {
					mChannels[0].noteOff(70);
				}
				
				try {
					Thread.sleep(delay/3);
				} catch (InterruptedException e) {

				}
				
				mChannels[0].noteOn(69, volume);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					mChannels[0].noteOff(70);
				}
				
				try {
					Thread.sleep(delay/3);
				} catch (InterruptedException e) {

				}
				mChannels[0].noteOn(65, volume);
				try {
					Thread.sleep(delay/2);
				} catch (InterruptedException e) {
					mChannels[0].noteOff(70);
				}
				
				try {
					Thread.sleep(delay/3);
				} catch (InterruptedException e) {

				}
			}

		} catch (MidiUnavailableException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		song1();
	}
}
