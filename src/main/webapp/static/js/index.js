/**
 * Created by heyanjing on 2017/12/18 8:38.
 */
console.log(111);

var str="persons[0][name][a]=heyanjing";
console.log(str);
console.log(str.replace(/(\[(\D+)])/,".$2"));
console.log(str.replace(/(\[(\D+)])/gi,".$2"));