use std::fs::File;
use std::fs::OpenOptions;
use std::fs::write;
use std::io;
use std::io::BufReader;
use std::io::prelude::*;

use clap::{App, Arg};

const FILE_NAME: &str = "remember-notes.txt";

fn main() {
    let matches = App::new("remember")
        .version("0.1.0")
        .author("Mario Codes Sánchez")
        .about("Shorthand for remember. Little CLI tool to fast-save ideas for new projects which may be developed in the future")
        .subcommand(
            App::new("add")
                .about("adds a new note")
                .help("add a new note to remember")
                .arg(
                    Arg::with_name("note content")
                        .help("content of the note we want to be able to remember")
                        .index(1)
                        .required(true)
                )
        )
        .subcommand(
            App::new("list")
                .aliases(&["list", "show"])
                .about("shows all notes")
                .help("shows in console all")
        )
        .subcommand(
            App::new("clean")
                .aliases(&["clean", "empty"])
                .about("deletes all notes, by emptying the file where notes are saved")
        )
        .subcommand(
            App::new("delete")
                .aliases(&["del", "delete"])
                .about("deletes one note by index on File, starting by 1")
                .arg(
                    Arg::with_name("note index")
                        .help("line number, the note we want to delete occupies at file. Starts by 1")
                        .index(1)
                        .required(true)
                )
        )
        .get_matches();

    if matches.subcommand_name().is_none() {
        println!("Welcome to remember, you may add new notes using rem add 'here goes your note'. Run rem --help to see options.");
    }

    if let Some(ref _matches) = matches.subcommand_matches("add") {
        let note = _matches.value_of("note content").unwrap();
        append_to_file(note);
    }

    if let Some(ref _matches) = matches.subcommand_matches("list") {
        list();
    }

    if let Some(ref _matches) = matches.subcommand_matches("clean") {
        delete_notes();
    }

    if let Some(ref _matches) = matches.subcommand_matches("delete") {
        let note_idx_str = _matches.value_of("note index").unwrap();
        let note_index = note_idx_str.parse::<i32>().unwrap();
        delete_single_note(note_index);
    }
}

fn append_to_file(_note: &str) {
    let mut file = OpenOptions::new()
        .create(true)
        .append(true)
        .open(FILE_NAME)
        .unwrap();

    if let Err(e) = writeln!(file, "{}", _note) {
        eprintln!("Couldn't write to file {}", e);
    }
}

fn list() {
    let file_contents = read_file_to_memory();
    let mut counter = 1;
    println!("To Remember:");
    for line in file_contents {
        println!("\t{}. {}", counter, line);
        counter += 1;
    }
}

fn delete_notes() {
    println!("Are you sure? This will delete all your saved notes (yes): ");
    let mut buffer = String::new();
    io::stdin()
        .lock()
        .read_line(&mut buffer)
        .expect("Couldn't read user input");

    if buffer.trim() == "yes" || buffer.trim() == "y" {
        overwrite_file();
        println!("Deleted all saved notes");
    }
}

fn delete_single_note(note_index: i32) {
    let mut file_contents = read_file_to_memory();
    overwrite_file();
    write_contents_but_index_line(note_index, &mut file_contents);
}

fn write_contents_but_index_line(note_index: i32, file_contents: &mut Vec<String>) {
    let mut counter = 1;
    for line in file_contents {
        if note_index != counter {
            // FIXME: It's possible to improve the performance here. I open the file every iteration.
            append_to_file(line.trim());
        }
        counter += 1;
    }
}

fn overwrite_file() {
    write(FILE_NAME, "")
        .expect("Couldn't open file");
}

fn read_file_to_memory() -> Vec<String> {
    // open original file
    let file = File::open(FILE_NAME)
        .expect("Error on open file");
    let reader = BufReader::new(file);

    // save its actual contents in memory
    let mut file_content = Vec::new();
    for line in reader.lines() {
        file_content.push(line.unwrap());
    }

    file_content
}