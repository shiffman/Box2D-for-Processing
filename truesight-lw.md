
# Table of Contents

1.  [TODO](#orgd4c1fd0)
    1.  [go through research proposal for things to add here](#orge2e1da5)
    2.  [go through lab notebook for things to add here](#org6b5d018)
2.  [Planning](#org79f41e8)
    1.  [Resources](#org01ed7b5)
        1.  [Rob Miles tips: https://luxaequoris.notion.site/Drafting-a-talk-The-Rob-Miles-Method-Actually-I-ve-added-a-bunch-of-SPJ-talk-tips-in-this-too-woo-9cdecd98bfb04075baccf0e2a75d41f5](#org250d2d2)
        2.  [Stuart Shieber: rational reconstruction](#orgfc216fa)
3.  [Reviewers](#orgc74a71e)
    1.  [Henry](#org9d335e7)
    2.  [Hugo](#orgb88deba)
    3.  [Jessica](#org74094e1)
    4.  [Seth Herd](#org75be727)
4.  [Takes](#orgde0d9f2)
    1.  [Take 1](#org4a002e4)
        1.  [Title](#orgbead827)
        2.  [Abstract (intro?):](#org7c2ce1f)
5.  [Paper version [organization useful but content moved to 'Alignment forum version']](#orgd52ce4f)
    1.  [Title](#orgc0fa876)
        1.  [Desiderata:](#org180f905)
    2.  [General](#org4d15db8)
        1.  [This is in some ways unsurprising: the entirety of what LLMs are trained on (in pre-training, which despite the name is the large majority of their training by compute or by data size) is impersonating the (often unknown) authors of texts well enough to generate (aka predict) that text token by token.](#org84f00c2)
    3.  [Types of truesight](#orgab5de0f)
        1.  [Identification by name (author) as a limiting case](#orga41926c)
        2.  [Demographics as tip of the iceberg](#org8221948)
        3.  [Personality, 'hopes and dreams'](#orga91aba2)
    4.  [Why does this matter?](#org3e7091e)
        1.  [I suspect that few users of ChatGPT understand](#org84966bb)
        2.  [Deception/manipulation](#org03d6c58)
        3.  [Understanding outcomes of RLHF &#x2013; does 'user' emerge? does 'self'?](#org1aabdfe)
        4.  [Privacy](#orgf291da2)
        5.  [Foundation for other metrics, eg manipulation capability as a function of user understanding](#orgb833099)
    5.  [Past work](#orgac0645d)
        1.  [Janus Truesight](#org35a30bc)
        2.  ['Beyond Memorization' aka Reddit &#x2013; the 1st part of mine is a recap of their work BUT (maybe make this a reveal) I've gotten rid of all the explicit cues and still get decent results!](#org0650242)
        3.  [Stylometry](#org45cad4b)
        4.  [Paul Riechers et al](#orgd2b8c0b)
    6.  [There are legitimate reasons to understand the user!](#orga033396)
        1.  [And as mentioned above, this is what LLM's **do**.](#org678935b)
        2.  [Explaining at the right level.](#org46a3a85)
        3.  [Knowing the user will find helpful.](#orgdc608d4)
    7.  [Results](#orgbb4e07e)
        1.  [Important: note that multi-class Brier ranges 0..2, not 0..1!](#org113b1a8)
    8.  [Desire to find a robust metric](#org31ea309)
        1.  [Non-demographic info, ie the bulk of the iceberg](#orgdcbe686)
        2.  [Metrics considered](#orgdf44c3e)
    9.  [Weaknesses](#orgc96a785)
        1.  [So far this is more about 'authors' than 'users', given the dataset.](#org05aeeb0)
    10. [Variations](#org60580e4)
        1.  [Useful to have a variations section maybe? Which contains](#orgd905ef4)
    11. [Future Work](#org65cc938)
        1.  [Interpretability](#org9d24864)
        2.  [Study of per token certainty](#org4be910c)
        3.  [Start to aim for deeper info about the user's beliefs](#org89f7c16)
        4.  [Find better ways to distinguish 'authors' / 'users'](#orgc3eb2b6)
        5.  [Experiment with models (eg llama-2, gemma) for which we have (otherwise equivalent) pre- and post-RLHF versions. This should help us begin to zero in on the model's model of the user in particular.](#org968b681)
        6.  [Models as proxy listeners.](#orgac49ca9)
    12. [Intended contributions](#org1d3c73e)
        1.  [Stylometry with GPT-4 / general purpose LLMs (TODO confirm that previous such research does not exist). Elimination of overt cues?](#orgf27ba23)
        2.  [Study of per token certainty](#org9b366f3)
        3.  [Robust metric](#org066840a)
        4.  [Examination of relevant concept vectors](#orgb908495)
        5.  [Interactive? Is that a contribution worth mentioning?](#org9ff9fdd)
    13. [Methodology notes](#org6684094)
        1.  [Data pruning](#orgc1223f6)
    14. [Citations](#orgdd47529)
        1.  [Anthropic persuasion paper as a baseline for persuasion ability which can presumably be further improved by knowledge of the user.](#org20256db)
        2.  [Cite Adam & Paul](#org7f2fe1f)
6.  [Alignment forum version](#org1604f81)
    1.  [Title](#org91b0212)
        1.  [Large LLMs infer significant information about people from their writing](#org02738ab)
    2.  [Opening](#orgd548f6c)
        1.  [Storytellish opening](#org460ab55)
        2.  [Paperish opening](#org7828307)
        3.  [Intro](#org429b450)
        4.  [Continued](#org7e42790)
    3.  [What we did](#org6097a44)
        1.  [We gave GPT-3.5-turbo-instruct essay text written by OKCupid users, 300 words long on average, and asked it to say whether it believed the author to be (for example) male or female [footnote: 2012, they're all male or female]. We treated the probability distribution over categories as a prediction (rather than just looking at the highest score), and calculated Brier scores for how good the model's predictions were. Note that these are multiclass Brier scores, which range from 0 (best) to 2 (worst) rather than standard two-way Brier scores, which range from 0 to 1. We tested the model's ability to infer gender, sexual orientation, college-education status, ethnicity, and age (bucketed into 0-30 vs 31-).](#org36a0766)
        2.  [Note that these demographic categories were not chosen for their importance, although they include some categories that some people might prefer to keep private. The advantage of these categories is that it's possible to find datasets which pair that information on with free-written text.](#org053181e)
        3.  [What actually matters more, in our view, is the model's ability to infer more nuanced information about authors, about their personality, their credulity, their levels of trust, what they believe, and so on. But those sorts of things are harder to measure, so we chose to start with demographics.](#orga6f6732)
        4.  [Mention web app](#orgdfeb7e9)
    4.  [Results](#orgd23fbdd)
        1.  [Source code (messy!)](#org9635a57)
        2.  [Important: note that multi-class Brier ranges 0..2, not 0..1!](#orgd447291)
    5.  [Past work](#org45ecc7e)
        1.  ['Beyond Memorization' aka Reddit &#x2013; the 1st part of mine is a recap of their work BUT (maybe make this a reveal) I've gotten rid of all the explicit cues and still get decent results!](#org8faf3f5)
        2.  [Janus Truesight](#org91858f7)
        3.  [Stylometry](#orge13e61f)
        4.  [Paul Riechers et al](#orgd1ff090)
    6.  [Future work](#org0427efb)
        1.  [Interpretability](#org8a5c7c6)
        2.  [Study of per token certainty](#orgf4507fb)
        3.  [Start to aim for deeper info about the user's beliefs](#org57e1e1d)
        4.  [Find better ways to distinguish 'authors' / 'users'](#orgfe0d6f9)
        5.  [Experiment with models (eg llama-2, gemma) for which we have (otherwise equivalent) pre- and post-RLHF versions. This should help us begin to zero in on the model's model of the user in particular.](#org9c2dc09)
        6.  [Models as proxy listeners.](#orgb562dce)
    7.  [Discussion/Conclusions](#org11acea8)
        1.  [This is in some ways unsurprising: the entirety of what LLMs are trained on (in pre-training, which despite the name is the large majority of their training by compute or by data size) is understanding the (often unknown) authors of texts well enough to generate (aka predict) that text token by token.](#org03c550e)
        2.  [An initial metric](#org63626c2)
        3.  [Understanding outcomes of RLHF &#x2013; does 'user' emerge? does 'self'?](#org97db2d6)
        4.  [There are legitimate reasons to understand the user!](#org2a19e61)
        5.  [Foundation for other metrics, eg manipulation capability as a function of user understanding](#org9f4979c)
    8.  [Appendices](#org410fd86)
        1.  [Citations [maybe distribute throughout with links for this version]](#org8f282ba)
        2.  [Methodology notes](#orgd11cca6)
        3.  [Variations](#orgb7a6988)

public:: true


<a id="orgd4c1fd0"></a>

# TODO


<a id="orge2e1da5"></a>

## TODO go through research proposal for things to add here


<a id="org6b5d018"></a>

## TODO go through lab notebook for things to add here


<a id="org79f41e8"></a>

# Planning


<a id="org01ed7b5"></a>

## Resources


<a id="org250d2d2"></a>

### Rob Miles tips: <https://luxaequoris.notion.site/Drafting-a-talk-The-Rob-Miles-Method-Actually-I-ve-added-a-bunch-of-SPJ-talk-tips-in-this-too-woo-9cdecd98bfb04075baccf0e2a75d41f5>

1.  or video: <https://www.youtube.com/watch?v=pmjOEu7V8YI&t=14373s>

2.  Publish in lots of formats (paper, blog, tweet, talks, podcast)

3.  Write for one step less knowledgeable than who you want to reach

4.  Get people's attention:

    1.  Title should answer 'do I want to read this?'. How you would describe what you did to the perfect audience member if you bumped into them and they only have a moment.

    2.  1st sentence should get people to read 2nd sentence

    3.  2nd sentence should get people to read the intro

    4.  Abstract should answer, 'do I want to keep reading this?'

5.  Progressive jpeg!

6.  Tell a story!

    1.  If you think you're not telling a story, you're telling a boring story

        1.  A problem

        2.  SO the first thing

        3.  BUT this other thing

        4.  SO the last thing


<a id="orgfc216fa"></a>

### Stuart Shieber: [rational reconstruction](https://web.stanford.edu/class/cs224u/readings/shieber-writing.pdf)


<a id="orgc74a71e"></a>

# Reviewers


<a id="org9d335e7"></a>

## Henry


<a id="orgb88deba"></a>

## Hugo


<a id="org74094e1"></a>

## Jessica


<a id="org75be727"></a>

## Seth Herd


<a id="orgde0d9f2"></a>

# Takes


<a id="org4a002e4"></a>

## Take 1


<a id="orgbead827"></a>

### Title


<a id="org7c2ce1f"></a>

### Abstract (intro?):

1.  We show that large LLMs can infer quite a lot about the authors of texts that they read. In particular, they can infer (at least) the author's gender, ethnicity, and education level rather accurately after seeing only a few hundred words of text. This raises privacy concerns, and suggests that such models will have better ability to deceive or manipulate their users.

2.  The privacy concerns are straightforward: regardless of whether the model itself is acting to violate users' privacy or someone is using the model to violate users' privacy, users might prefer that the models they interact with not routinely infer their gender, ethnicity, or for that matter their personal beliefs.

    1.  I suspect that few users of ChatGPT understand

        1.  People have pretty different intuitions about how obvious this is and whether it's a problem.

            1.  Is it ok if they know what you understand and what you don't?

            2.  Is it ok if they know your darkest sexual desires?

            3.  Is it ok if they know the most shameful thing you've ever done?

3.  The reason for concern about deception and manipulation might take a bit more clarification. What does a speaker, human or artificial, need to do in order to do a good job of deceiving or manipulating a listener? We believe that one important and understudied factor is having a good model of the listener and the listener's beliefs. If the speaker says something outside the bounds of what the listener finds plausible, the listener won't believe it. Further, the listener is likely to lose trust in the speaker, and that trust is hard to regain. Strategically deceptive or manipulative speakers need to maintain that trust over an extended period of time, and this is extremely difficult to do without knowing how credulous the listener is, and what they believe.

4.  Future web app


<a id="orgd52ce4f"></a>

# Paper version [organization useful but content moved to 'Alignment forum version']


<a id="orgc0fa876"></a>

## Title


<a id="org180f905"></a>

### Desiderata:

1.  Answers the question, 'Do I want to read this?'


<a id="org4d15db8"></a>

## General


<a id="org84f00c2"></a>

### This is in some ways unsurprising: the entirety of what LLMs are trained on (in pre-training, which despite the name is the large majority of their training by compute or by data size) is impersonating the (often unknown) authors of texts well enough to generate (aka predict) that text token by token.


<a id="orgab5de0f"></a>

## Types of truesight


<a id="orga41926c"></a>

### Identification by name (author) as a limiting case

1.  Some examples

    1.  Share a playground

2.  But this is not the situation most of us are in


<a id="org8221948"></a>

### Demographics as tip of the iceberg


<a id="orga91aba2"></a>

### Personality, 'hopes and dreams'


<a id="org3e7091e"></a>

## Why does this matter?


<a id="org84966bb"></a>

### I suspect that few users of ChatGPT understand

1.  People have pretty different intuitions about how obvious this is and whether it's a problem.

    1.  Is it ok if they know what you understand and what you don't?

    2.  Is it ok if they know your darkest sexual desires?

    3.  Is it ok if they know the most shameful thing you've ever done?


<a id="org03d6c58"></a>

### Deception/manipulation


<a id="org1aabdfe"></a>

### Understanding outcomes of RLHF &#x2013; does 'user' emerge? does 'self'?

1.  See Viégas/Wattenberg


<a id="orgf291da2"></a>

### Privacy


<a id="orgb833099"></a>

### Foundation for other metrics, eg manipulation capability as a function of user understanding


<a id="orgac0645d"></a>

## Past work


<a id="org35a30bc"></a>

### Janus Truesight

1.  <https://www.lesswrong.com/posts/doPbyzPgKdjedohud/the-case-for-more-ambitious-language-model-evals>

2.  gwern: <https://www.lesswrong.com/posts/doPbyzPgKdjedohud/the-case-for-more-ambitious-language-model-evals#XZFTx2ek8G8stBKW4>

3.  <https://cyborgism.wiki/hypha/truesight>

4.  (minor): <https://www.lesswrong.com/posts/doPbyzPgKdjedohud/the-case-for-more-ambitious-language-model-evals#JDsJSgfeovg9xobab>

    1.  'The reason truesight works (more than one might naively expect) is probably mostly that there's mountains of evidence everywhere (compared to naively expected). Models don't need to be superhuman except in breadth of knowledge to be potentially qualitatively superhuman in effects downstream of truesight-esque capabilities because humans are simply unable to integrate the plenum of correlations.'

5.  (really minor) <https://www.lesswrong.com/posts/tbJdxJMAiehewGpq2/impressions-from-base-gpt-4#CemTb7c2gmwuSAwCC>


<a id="org0650242"></a>

### 'Beyond Memorization' aka Reddit &#x2013; the 1st part of mine is a recap of their work BUT (maybe make this a reveal) I've gotten rid of all the explicit cues and still get decent results!


<a id="org45cad4b"></a>

### Stylometry

1.  TODO really need to read a lit review on stylometry, I think there's one in my lab notebook

2.  Stylometry is 'author', this is 'user'

3.  'author' is always present; 'user' maybe emerges during RLHF

4.  We're considering the set of cases where the token-generator is not significantly represented in the training data


<a id="orgd2b8c0b"></a>

### Paul Riechers et al

1.  Broader / more abstract view:

    1.  Token stream coming from 1 of n token-generating Markov processes

    2.  How does the model 'synchronize' with the correct process? How long does that take? How much information is needed?


<a id="orga033396"></a>

## There are legitimate reasons to understand the user!


<a id="org678935b"></a>

### And as mentioned above, this is what LLM's **do**.


<a id="org46a3a85"></a>

### Explaining at the right level.


<a id="orgdc608d4"></a>

### Knowing the user will find helpful.


<a id="orgbb4e07e"></a>

## Results


<a id="org113b1a8"></a>

### Important: note that multi-class Brier ranges 0..2, not 0..1!


<a id="org31ea309"></a>

## Desire to find a robust metric


<a id="orgdcbe686"></a>

### Non-demographic info, ie the bulk of the iceberg

1.  Correct way to explain to a particular user (vocabulary / level of depth / etc)

    1.  8yo vs grad student

2.  Consider that knowing someone's gender, age, education level, etc would not be nearly sufficient to convincingly impersonate them

    1.  Ezra tentatively suggests: we understand someone to the extent we can predict them.


<a id="orgdf44c3e"></a>

### Metrics considered

1.  Can the model impersonate the user well enough to fool an observer, where observer could be human or LLM or specialized stylometric tool

    1.  Note that this is perhaps context-dependent, and also observer-dependent (although it could be relative to a standard observer, like the Shannon metric for natural languages is).

2.  As the model understands the user better, we should expect its average per-word perplexity to decrease, presumably asymptotically approaching something like 'full understanding of the user'.

3.  Comparing (perplexity on the text) to (perplexity on the text after telling the model up front who the author is).

4.  How well the model can pick the user out of a universe of n (possible or actual) users.

    1.  Eigenauthors?


<a id="orgc96a785"></a>

## Weaknesses


<a id="org05aeeb0"></a>

### So far this is more about 'authors' than 'users', given the dataset.

1.  Maybe this is OK? At some point it's important to tackle the 'users' problem, but preliminary results can maybe just be authors.


<a id="org60580e4"></a>

## Variations


<a id="orgd905ef4"></a>

### Useful to have a variations section maybe? Which contains

1.  What if we calibrate?

2.  What if we omit giveaway words? (NB I'm currently always doing that for gender)

3.  What if we don't omit nonstandard answers (see 4/22 in results notebook)?


<a id="org65cc938"></a>

## Future Work


<a id="org9d24864"></a>

### Interpretability

1.  This initially started as primarily an interpretation project, but work that only considers output logits has proved fruitful enough so far.

2.  Examine relevant concept vectors


<a id="org4be910c"></a>

### Study of per token certainty


<a id="org89f7c16"></a>

### Start to aim for deeper info about the user's beliefs


<a id="orgc3eb2b6"></a>

### Find better ways to distinguish 'authors' / 'users'

1.  Get user data eg mech turk

2.  ShareGPT


<a id="org968b681"></a>

### Experiment with models (eg llama-2, gemma) for which we have (otherwise equivalent) pre- and post-RLHF versions. This should help us begin to zero in on the model's model of the user in particular.


<a id="orgac49ca9"></a>

### Models as proxy listeners.


<a id="org1d3c73e"></a>

## Intended contributions


<a id="orgf27ba23"></a>

### Stylometry with GPT-4 / general purpose LLMs (TODO confirm that previous such research does not exist). Elimination of overt cues?


<a id="org9b366f3"></a>

### Study of per token certainty


<a id="org066840a"></a>

### Robust metric


<a id="orgb908495"></a>

### Examination of relevant concept vectors


<a id="org9ff9fdd"></a>

### Interactive? Is that a contribution worth mentioning?


<a id="org6684094"></a>

## Methodology notes


<a id="orgc1223f6"></a>

### Data pruning

1.  Using the OKCupid dataset, because it couples the answers to 10 essay questions [TODO include essay prompts, sample essay responses] to demographics of the authors.

2.  Eliminate any which contain a synonym for male or female. This eliminates 72.4% of the data rows, some of which are false positives; this could potentially skew the data a bit, but spot checking doesn't suggest problems. This only decreases accuracy on gender from 90% to 88%.

3.  Eliminating profiles with fewer than 400 characters in the essay sections, on the order of 1%.

4.  Sanity checking on Persuade dataset because it's too new to be in the training data. 80% rather than 90% on gender.

5.  Omitting profiles with non-standard answers (eg ethnicity 'Asian Pacific / White / other').


<a id="orgdd47529"></a>

## Citations


<a id="org20256db"></a>

### Anthropic persuasion paper as a baseline for persuasion ability which can presumably be further improved by knowledge of the user.


<a id="org7f2fe1f"></a>

### Cite Adam & Paul


<a id="org1604f81"></a>

# Alignment forum version


<a id="org91b0212"></a>

## Title


<a id="org02738ab"></a>

### Large LLMs infer significant information about people from their writing


<a id="orgd548f6c"></a>

## Opening


<a id="org460ab55"></a>

### Storytellish opening

1.  Like many, I was quite startled [when janus showed](https://www.lesswrong.com/posts/doPbyzPgKdjedohud/the-case-for-more-ambitious-language-model-evals?commentId=XZFTx2ek8G8stBKW4) that gpt-4-base could identify @gwern with 92% confidence from a 300-word comment. If current models can infer information about text authors that quickly, it poses risks to privacy, but also means that future misaligned models are in a much better position to deceive or manipulate their users.


<a id="org7828307"></a>

### Paperish opening

1.  We show that large LLMs can infer quite a lot about the authors of texts that they read. In particular, they can infer (at least) the author's gender, ethnicity, and education level rather accurately after seeing only a few hundred words of text. These demographics are the easiest thing to test accuracy on with existing datasets, but (we believe) only the tip of the iceberg. This raises privacy concerns, and means we should be more concerned that near-future models will be able to effectively deceive or manipulate their users.


<a id="org429b450"></a>

### Intro

1.  The privacy concerns are straightforward: regardless of whether the model itself is acting to violate users' privacy or someone is using the model to violate users' privacy, users might prefer that the models they interact with not routinely infer their gender, their ethnicity, or their personal beliefs.

    1.  I suspect that few users of ChatGPT understand

        1.  People have pretty different intuitions about how obvious this is and whether it's a problem.

            1.  Is it ok if they know what you understand and what you don't?

            2.  Is it ok if they know your darkest sexual desires?

            3.  Is it ok if they know the most shameful thing you've ever done?

2.  The reason for concern about deception and manipulation might take a bit more clarification. What does a speaker, human or artificial, need to do in order to do a good job of deceiving or manipulating a listener? We believe that one important and understudied factor is having a good model of the listener and the listener's beliefs. If the speaker says something outside the bounds of what the listener finds plausible, the listener won't believe it. Further, the listener is likely to lose trust in the speaker, and that trust is hard to regain. Strategically deceptive or manipulative speakers need to maintain that trust over an extended period of time, and this is extremely difficult to do without knowing what the listener is like, and what they believe.


<a id="org7e42790"></a>

### Continued

1.  Of course, most of us aren't well-known and prolific writers like Gwern, with several billion words<sup><a id="fnr.1" class="footref" href="#fn.1" role="doc-backlink">1</a></sup> of text in the LLM training data. What can LLMs figure out about the rest of us?

2.  As [recent work](https://www.alignmentforum.org/posts/gTZ2SxesbHckJ3CkF/transformers-represent-belief-state-geometry-in-their) from Adam Shai & collaborators shows, transformers learn to understand and sync to the processes generating the input they see.

3.  Humans are extremely complex and difficult to predict (although note that LLMs are superhuman at doing so!)

    1.  TODO compare directly to Shannon's ['entropy of English' stat](https://benkrause.github.io/blog/human-level-text-prediction/) (~1.3 bits per character)

        1.  Byte-pair encoding ~= 4-6 chars per token


<a id="org6097a44"></a>

## What we did


<a id="org36a0766"></a>

### We gave GPT-3.5-turbo-instruct essay text written by OKCupid users, 300 words long on average, and asked it to say whether it believed the author to be (for example) male or female [footnote: 2012, they're all male or female]. We treated the probability distribution over categories as a prediction (rather than just looking at the highest score), and calculated Brier scores for how good the model's predictions were. Note that these are multiclass Brier scores, which range from 0 (best) to 2 (worst) rather than standard two-way Brier scores, which range from 0 to 1. We tested the model's ability to infer gender, sexual orientation, college-education status, ethnicity, and age (bucketed into 0-30 vs 31-).


<a id="org053181e"></a>

### Note that these demographic categories were not chosen for their importance, although they include some categories that some people might prefer to keep private. The advantage of these categories is that it's possible to find datasets which pair that information on with free-written text.


<a id="orga6f6732"></a>

### What actually matters more, in our view, is the model's ability to infer more nuanced information about authors, about their personality, their credulity, their levels of trust, what they believe, and so on. But those sorts of things are harder to measure, so we chose to start with demographics.


<a id="orgdfeb7e9"></a>

### Mention web app


<a id="orgd23fbdd"></a>

## Results


<a id="org9635a57"></a>

### Source code (messy!)


<a id="orgd447291"></a>

### Important: note that multi-class Brier ranges 0..2, not 0..1!


<a id="org45ecc7e"></a>

## Past work


<a id="org8faf3f5"></a>

### 'Beyond Memorization' aka Reddit &#x2013; the 1st part of mine is a recap of their work BUT (maybe make this a reveal) I've gotten rid of all the explicit cues and still get decent results!

1.  They do touch on this slightly in their 'Client-Side Anonymization' subsection in section 6.

2.  Different categories, different data

3.  Prefaces different approaches


<a id="org91858f7"></a>

### Janus Truesight

1.  Identification by name (author) as a limiting case

    1.  Some examples

        1.  Share a playground

    2.  But this is not the situation most of us are in

2.  <https://www.lesswrong.com/posts/doPbyzPgKdjedohud/the-case-for-more-ambitious-language-model-evals>

3.  gwern: <https://www.lesswrong.com/posts/doPbyzPgKdjedohud/the-case-for-more-ambitious-language-model-evals#XZFTx2ek8G8stBKW4>

4.  <https://cyborgism.wiki/hypha/truesight>

5.  (minor): <https://www.lesswrong.com/posts/doPbyzPgKdjedohud/the-case-for-more-ambitious-language-model-evals#JDsJSgfeovg9xobab>

    1.  'The reason truesight works (more than one might naively expect) is probably mostly that there's mountains of evidence everywhere (compared to naively expected). Models don't need to be superhuman except in breadth of knowledge to be potentially qualitatively superhuman in effects downstream of truesight-esque capabilities because humans are simply unable to integrate the plenum of correlations.'

6.  (really minor) <https://www.lesswrong.com/posts/tbJdxJMAiehewGpq2/impressions-from-base-gpt-4#CemTb7c2gmwuSAwCC>


<a id="orge13e61f"></a>

### Stylometry

1.  TODO really need to read a lit review on stylometry, I think there's one in my lab notebook

2.  Stylometry is 'author', this is 'user'

3.  'author' is always present; 'user' maybe emerges during RLHF

4.  We're considering the set of cases where the token-generator is not significantly represented in the training data


<a id="orgd1ff090"></a>

### Paul Riechers et al

1.  Broader / more abstract view:

    1.  Token stream coming from 1 of n token-generating Markov processes

    2.  How does the model 'synchronize' with the correct process? How long does that take? How much information is needed?


<a id="org0427efb"></a>

## Future work


<a id="org8a5c7c6"></a>

### Interpretability

1.  This initially started as primarily an interpretation project, but work that only considers output logits has proved fruitful enough so far.

2.  Examine relevant concept vectors


<a id="orgf4507fb"></a>

### Study of per token certainty


<a id="org57e1e1d"></a>

### Start to aim for deeper info about the user's beliefs


<a id="orgfe0d6f9"></a>

### Find better ways to distinguish 'authors' / 'users'

1.  Get user data eg mech turk

2.  ShareGPT


<a id="org9c2dc09"></a>

### Experiment with models (eg llama-2, gemma) for which we have (otherwise equivalent) pre- and post-RLHF versions. This should help us begin to zero in on the model's model of the user in particular.


<a id="orgb562dce"></a>

### Models as proxy listeners.


<a id="org11acea8"></a>

## Discussion/Conclusions


<a id="org03c550e"></a>

### This is in some ways unsurprising: the entirety of what LLMs are trained on (in pre-training, which despite the name is the large majority of their training by compute or by data size) is understanding the (often unknown) authors of texts well enough to generate (aka predict) that text token by token.


<a id="org63626c2"></a>

### An initial metric

1.  Main metric

2.  Other metrics considered [just link to google doc]

    1.  Can the model impersonate the user well enough to fool an observer, where observer could be human or LLM or specialized stylometric tool

        1.  Note that this is perhaps context-dependent, and also observer-dependent (although it could be relative to a standard observer, like the Shannon metric for natural languages is).

    2.  As the model understands the user better, we should expect its average per-word perplexity to decrease, presumably asymptotically approaching something like 'full understanding of the user'.

    3.  Comparing (perplexity on the text) to (perplexity on the text after telling the model up front who the author is).

    4.  How well the model can pick the user out of a universe of n (possible or actual) users.

        1.  Eigenauthors?


<a id="org97db2d6"></a>

### Understanding outcomes of RLHF &#x2013; does 'user' emerge? does 'self'?

1.  See Viégas/Wattenberg


<a id="org2a19e61"></a>

### There are legitimate reasons to understand the user!

1.  And as mentioned above, this is what LLM's **do**.

2.  Explaining at the right level.

3.  Knowing the user will find helpful.


<a id="org9f4979c"></a>

### Foundation for other metrics, eg manipulation capability as a function of user understanding


<a id="org410fd86"></a>

## Appendices


<a id="org8f282ba"></a>

### Citations [maybe distribute throughout with links for this version]

1.  Anthropic persuasion paper as a baseline for persuasion ability which can presumably be further improved by knowledge of the user.

2.  Cite Adam & Paul


<a id="orgd11cca6"></a>

### Methodology notes

1.  Why Brier scores? [footnote]

    1.  Why not CE loss?

2.  Data pruning

    1.  Using the OKCupid dataset, because it couples the answers to 10 essay questions [TODO include essay prompts, sample essay responses] to demographics of the authors.

    2.  Eliminate any which contain a synonym for male or female. This eliminates 72.4% of the data rows, some of which are false positives; this could potentially skew the data a bit, but spot checking doesn't suggest problems. This only decreases accuracy on gender from 90% to 88%.

    3.  Eliminating profiles with fewer than 400 characters in the essay sections, on the order of 1%.

    4.  Sanity checking on Persuade dataset because it's too new to be in the training data. 80% rather than 90% on gender.

    5.  Omitting profiles with non-standard answers (eg ethnicity 'Asian Pacific / White / other').


<a id="orgb7a6988"></a>

### Variations

1.  What if we calibrate?

2.  What if we omit giveaway words? (NB I'm currently always doing that for gender)

3.  What if we don't omit nonstandard answers (see 4/22 in results notebook)?


# Footnotes

<sup><a id="fn.1" href="#fnr.1">1</a></sup> or, I don't know, a whole lot
