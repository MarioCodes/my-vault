<?php
    function wordOk($word_to_check) {
        for($x = 0; $x < strlen($word_to_check); $x++) {
            if($word_to_check[$x] != "_") return false;
        }
        return true;
    }

    function checkWord($word_to_check, $tiles) {
        for($x = 0; $x < strlen($word_to_check); $x++) { //each letter.
            $letter_match = false;
            for($i = 0; $i < strlen($tiles); $i++) { //each tile.
                if($tiles[$i] == $word_to_check[$x] && !$letter_match) {
                    $letter_match = true;
                    $word_to_check[$x] = "_";
                }
            }
        }
        return wordOk($word_to_check);
    }

    function longest($tiles) {
        $lineas = file('palabras.txt');
        $longest_word = "";

        foreach ($lineas as $word_to_check) { //each word in array.
            if(checkWord($word_to_check, $tiles) && (strlen($word_to_check) > strlen($longest_word))) $longest_word = $word_to_check;
        }

        echo $longest_word;
    }

    longest("dcthoyueorza");
?>