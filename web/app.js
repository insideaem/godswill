Ext.application({
    name : 'GW',
    autoCreateViewport : true,

    models : [ 'bible.BibleElement', 'bible.Bible', 'bible.Book', 'bible.Chapter', 'bible.Verse' ],
    controllers : [ 'BibleReader' ]
});